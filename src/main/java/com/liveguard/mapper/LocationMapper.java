package com.liveguard.mapper;

import com.liveguard.domain.Location;
import com.liveguard.dto.LocationDTO;

public class LocationMapper {

    public static Location locationDTOToLocation(LocationDTO locationDTO) {
        Location location = new Location();
        location.setLat(locationDTO.getLat());
        location.setLng(locationDTO.getLng());

        return location;
    }

    public static LocationDTO locationToLocationDTO(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(location.getId());
        locationDTO.setLng(location.getLng());
        locationDTO.setLat(location.getLat());
        locationDTO.setDate(location.getDate());
        locationDTO.setChipId(location.getChip().getId());

        return locationDTO;
    }

}
