package com.liveguard.service;

import com.liveguard.domain.Location;

public interface SendLocationService {

    void sendLocationInChipChannel(Location location);
}
