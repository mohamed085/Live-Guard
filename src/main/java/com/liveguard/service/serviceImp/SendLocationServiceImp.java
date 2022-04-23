package com.liveguard.service.serviceImp;

import com.liveguard.domain.Location;
import com.liveguard.dto.LocationDTO;
import com.liveguard.mapper.LocationMapper;
import com.liveguard.service.SendLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendLocationServiceImp implements SendLocationService {

    private final SimpMessagingTemplate template;

    public SendLocationServiceImp(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    @Async
    public void sendLocationInChipChannel(Location location) {
        log.debug("SendLocationService | sendLocationInChipChannel | chip id: " + location.getChip().getId());

        String destination = "/live-guard/chip/" + location.getChip().getId();
        LocationDTO locationDTO = LocationMapper.locationToLocationDTO(location);

        log.debug("SendLocationService | sendLocationInChipChannel | destination: " + destination);
        log.debug("SendLocationService | sendLocationInChipChannel | message: " + locationDTO);

        template.convertAndSend(destination, locationDTO);
    }
}
