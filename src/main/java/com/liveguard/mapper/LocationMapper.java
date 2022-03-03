package com.liveguard.mapper;

import com.liveguard.domain.Location;
import com.liveguard.dto.ChipSimpleDataDTO;
import com.liveguard.dto.LocationDTO;

public class LocationMapper {

    public static Location locationDTOToLocation(LocationDTO locationDTO) {
        Location location = new Location();
        location.setLat(location.getLat());
        location.setLng(location.getLng());

        return location;
    }

    public static LocationDTO locationToLocationDTO(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(location.getId());
        locationDTO.setLng(location.getLng());
        locationDTO.setLat(location.getLat());
        locationDTO.setDate(location.getDate());
        locationDTO.setChip(new ChipSimpleDataDTO(location.getChip().getId(), location.getChip().getName(), null, location.getChip().getPhoto()));
        locationDTO.setChipId(location.getChip().getId());

        return locationDTO;
    }

}
