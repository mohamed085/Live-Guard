package com.liveguard.service;

import com.liveguard.domain.ChipUser;

import java.util.List;

public interface ChipUserService {

    void save(ChipUser chipUser);

    ChipUser findByChipIdAndUserId(Long chipId, Long userId);

    Boolean existsByChipIdAndUserId(Long chipId, Long userId);

    void deleteByChipIdAndUserId(Long chipId, Long userId);

    List<ChipUser> findAllByUserId(Long userId);

}
