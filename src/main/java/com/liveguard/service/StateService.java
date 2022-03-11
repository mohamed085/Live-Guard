package com.liveguard.service;

import com.liveguard.domain.State;
import com.liveguard.dto.StateDTO;

import java.util.List;

public interface StateService {

    List<State> findByCountryIdOrderByNameAsc(Long countryId);

    Boolean isExist(String name);

    State findById(Long id);

    void add(StateDTO stateDTO);

    void update(Long id, StateDTO stateDTO);

    void deleteById(Long id);

}
