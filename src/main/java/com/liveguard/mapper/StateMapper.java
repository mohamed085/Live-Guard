package com.liveguard.mapper;

import com.liveguard.domain.State;
import com.liveguard.dto.StateDTO;

public class StateMapper {

    public static State stateDTOToState(StateDTO stateDTO) {
        return new State(stateDTO.getName());
    }

    public static StateDTO stateToStateDTO(State state) {
        return new StateDTO(state.getId(),
                state.getName(),
                state.getCountry().getName(),
                state.getCountry().getId());
    }
}
