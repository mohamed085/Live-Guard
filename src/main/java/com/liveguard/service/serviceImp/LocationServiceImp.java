package com.liveguard.service.serviceImp;

import com.liveguard.domain.*;
import com.liveguard.dto.LocationDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.LocationMapper;
import com.liveguard.repository.LocationRepository;
import com.liveguard.service.*;
import com.liveguard.util.CaseTransfer;
import com.liveguard.util.GFG;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocationServiceImp implements LocationService {

    private final LocationRepository locationRepository;
    private final ChipService chipService;
    private final SendLocationService sendLocationService;
    private final DayService dayService;
    private final TaskService taskService;
    private final UserTaskMuteService userTaskMuteService;
    private final SendNotificationService sendNotificationService;

    public LocationServiceImp(LocationRepository locationRepository, ChipService chipService,
                              SendLocationService sendLocationService, DayService dayService,
                              TaskService taskService, UserTaskMuteService userTaskMuteService,
                              SendNotificationService sendNotificationService) {
        this.locationRepository = locationRepository;
        this.chipService = chipService;
        this.sendLocationService = sendLocationService;
        this.dayService = dayService;
        this.taskService = taskService;
        this.userTaskMuteService = userTaskMuteService;
        this.sendNotificationService = sendNotificationService;
    }

    @Override
    @Transactional
    public void add(LocationDTO locationDTO) {
        log.debug("LocationService | add | locationDTO: " + locationDTO);

        Chip chip = chipService.findById(locationDTO.getChipId());

        Location location = LocationMapper.locationDTOToLocation(locationDTO);
        location.setDate(LocalDateTime.now());
        location.setChip(chip);

        try {
            /** Save in database */
            Location savedLocation = locationRepository.save(location);

            /** Send in chip channel */
            sendLocationService.sendLocationInChipChannel(savedLocation);

            /** Get Current Task */
            List<Task> currentTasks = getCurrentTasks(savedLocation);

            currentTasks.forEach(task -> {
                Double distance = GFG.distance(location.getLat(), task.getLat(), location.getLng(), task.getLng());
                log.debug("LocationService | add | currentTasks | task id: " + task.getId());
                log.debug("LocationService | add | currentTasks | task area: " + task.getArea());
                log.debug("LocationService | add | currentTasks | distance: " + distance);

                if (distance > task.getArea()) {
                    log.warn("LocationService | add | currentTasks | UNSAFE");
                    updateStatus(savedLocation.getId(), LocationStatus.UNSAFE);
                    userTaskMuteService.findAllByTaskIdAndStatus(task.getId(), false).forEach(userTaskMute -> {
                        sendNotificationService.sendPrivateNotification(generateNotification(LocationStatus.UNSAFE, userTaskMute.getTask().getRingtone(), chip), userTaskMute.getUser());
                    });

                }
                else {
                    log.debug("LocationService | add | currentTasks | SAFE");
                    updateStatus(savedLocation.getId(), LocationStatus.SAFE);
                }
            });

            /**
             * TODO
             * Check location
             * Push Notification if status out
             */

        } catch (Exception e) {
            e.printStackTrace();
            log.error("LocationService | add | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateStatus(Long id, LocationStatus status) {
        log.debug("LocationService | updateStatus | id: " + id);
        log.debug("LocationService | updateStatus | status: " + status);

        try {
            locationRepository.updateStatus(id, status);
        } catch (Exception e) {
            log.error("LocationService | updateStatus | error: " + e.getMessage());
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private List<Task> getCurrentTasks(Location location) {
        log.debug("LocationService | add | getCurrentTasks");
        DayOfWeek dayOfWeek = location.getDate().getDayOfWeek();
        String day = dayOfWeek.name();
        log.debug("LocationService | add | getCurrentTasks | current day: " + day);

        EnumDay day1 = EnumDay.valueOf(CaseTransfer.toLowerCaseExpectedFirstLetter(day));

        try {
            Day foundDay = dayService.findByDay(day1);
            log.debug("LocationService | add | getCurrentTasks | current day id: " + foundDay.getId());

            LocalTime time = location.getDate().toLocalTime();
            log.debug("LocationService | add | getCurrentTasks | current time: " + time);

            List<Task> tasks = taskService
                    .findByChipIdAndRepeatEquals(location.getChip().getId(), foundDay)
                    .stream()
                    .filter(task -> task.getStartTime().isBefore(time) && task.getEndTime().isAfter(time))
                    .collect(Collectors.toList());

            log.debug("LocationService | add | getCurrentTasks | current tasks: " + tasks);
            return tasks;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Notification generateNotification(LocationStatus status, String ringtone, Chip chip) {

        String content = "";

        if (status.equals(LocationStatus.UNSAFE)) {
            content = "You child in a danger place";
        }

        Notification notification = new Notification();
        notification.setSubject(chip.getName());
        notification.setPhoto(chip.getPhoto());
        notification.setCreatedTime(LocalDateTime.now());
        notification.setRingtone(ringtone);
        notification.setContent(content);

        log.debug("LocationService | add | generateNotification | subject: " + notification.getSubject());
        log.debug("LocationService | add | generateNotification | photo: " + notification.getPhoto());
        log.debug("LocationService | add | generateNotification | content: " + notification.getContent());
        log.debug("LocationService | add | generateNotification | ringtone: " + notification.getRingtone());
        log.debug("LocationService | add | generateNotification | created time: " + notification.getCreatedTime());

        return notification;
    }

}
