package com.liveguard.service;

import com.liveguard.domain.ChipAssociatedDetails;
import com.liveguard.dto.ChipAssociatedDetailsDTO;

import java.io.IOException;

public interface ChipAssociatedDetailsService {

    ChipAssociatedDetails addChipAssociatedDetails(Long chipId, ChipAssociatedDetailsDTO chipAssociatedDetailsDTO);

}
