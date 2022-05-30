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

    void addNewUserToMyChip(Long userId, Long chipId);

    void removeNormalUserFromMyChip(Long userId, Long chipId);

    ChipUser findById(Long id);

    Boolean existByChipIdAndUserId(Long chipId, Long userId);

    List<ChipUser> findAllByChipId(Long chipId);
}
