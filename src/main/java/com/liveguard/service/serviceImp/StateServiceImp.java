package com.liveguard.service.serviceImp;

import com.liveguard.domain.Country;
import com.liveguard.domain.State;
import com.liveguard.dto.StateDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.StateMapper;
import com.liveguard.repository.StateRepository;
import com.liveguard.service.CountryService;
import com.liveguard.service.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StateServiceImp implements StateService {

    private final StateRepository stateRepository;
    private final CountryService countryService;

    public StateServiceImp(StateRepository stateRepository, CountryService countryService) {
        this.stateRepository = stateRepository;
        this.countryService = countryService;
    }

    @Override
    public List<State> findByCountryIdOrderByNameAsc(Long countryId) {
        log.debug("StateService | isExist | country id: " + countryId);
        try {
            return stateRepository.findByCountryIdOrderByNameAsc(countryId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean isExist(String name) {
        log.debug("StateService | isExist | name: " + name);
        try {
            return stateRepository.existsByName(name);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public State findById(Long id) {
        log.debug("StateService | findById | id: " + id);
        try {
            return stateRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("State not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void add(StateDTO stateDTO) {
        log.debug("StateService | add | name: " + stateDTO.getName());
        log.debug("StateService | add | countryId: " + stateDTO.getCountryId());

        Country country = countryService.findById(stateDTO.getCountryId());
        isExist(stateDTO.getName());

        State state = StateMapper.stateDTOToState(stateDTO);
        state.setCountry(country);
        log.debug("StateService | add | state: " + state.toString());

        try {
            stateRepository.save(state);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void update(Long id, StateDTO stateDTO) {

    }

    @Override
    public void deleteById(Long id) {
        log.debug("StateService | deleteById | id: " + id);
        try {
            stateRepository.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
