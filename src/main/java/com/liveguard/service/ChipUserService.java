package com.liveguard.service;

import com.liveguard.domain.ChipUser;
import com.liveguard.dto.ChipUserDTO;
import com.liveguard.payload.AddNewChipRequest;

import java.util.List;

public interface ChipUserService {

    void add(AddNewChipRequest addNewChipRequest);

    List<ChipUser> findAllByUser();

    void updateMyChipInfo(ChipUserDTO chipUserDTO);

    void updateMyChipDetails(ChipUserDTO chipUserDTO);

    List<ChipUser> findAllUsersInChip(Long chipId);
}
