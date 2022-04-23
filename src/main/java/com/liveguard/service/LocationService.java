package com.liveguard.service;

import com.liveguard.domain.Location;
import com.liveguard.domain.LocationStatus;
import com.liveguard.dto.LocationDTO;

public interface LocationService {

    void add(LocationDTO locationDTO);

    void updateStatus(Long id, LocationStatus status);

}
