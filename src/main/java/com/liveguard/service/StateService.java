package com.liveguard.service;

import com.liveguard.domain.State;
import com.liveguard.dto.StateDTO;

import java.util.List;

public interface StateService {

    List<State> findAllByCountryId(Long countryId);

    void save(StateDTO stateDTO);

    Boolean isExist(Long countryId, String state);

    void delete(Long stateId);
}
