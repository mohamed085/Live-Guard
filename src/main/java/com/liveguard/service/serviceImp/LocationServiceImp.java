package com.liveguard.service.serviceImp;

import com.liveguard.domain.Chip;
import com.liveguard.domain.Location;
import com.liveguard.dto.LocationDTO;
import com.liveguard.mapper.LocationMapper;
import com.liveguard.repository.LocationRepository;
import com.liveguard.service.ChipService;
import com.liveguard.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class LocationServiceImp implements LocationService {

    private final LocationRepository locationRepository;
    private final ChipService chipService;

    public LocationServiceImp(LocationRepository locationRepository, ChipService chipService) {
        this.locationRepository = locationRepository;
        this.chipService = chipService;
    }

    @Override
    public Location add(LocationDTO locationDTO) {
        log.debug("chipService | add | chip id: " + locationDTO.getChipId());
        Chip chip = chipService.findById(locationDTO.getChipId());
        Location location = LocationMapper.locationDTOToLocation(locationDTO);
        location.setDate(LocalDateTime.now());
        location.setChip(chip);
        Location savedLocation = locationRepository.save(location);


        return null;
    }
}
