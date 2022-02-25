package com.liveguard.service;

import com.liveguard.domain.Location;
import com.liveguard.dto.LocationDTO;

public interface LocationService {

    Location add(LocationDTO locationDTO);

}
