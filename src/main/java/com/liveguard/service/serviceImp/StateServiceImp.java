package com.liveguard.service.serviceImp;

import com.liveguard.domain.State;
import com.liveguard.dto.StateDTO;
import com.liveguard.exception.BusinessException;
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
    public List<State> findAllByCountryId(Long countryId) {
        log.debug("StateService | findAllByCountryId | countryId: " + countryId);

        try {
            return stateRepository.findByCountryIdOrderByNameAsc(countryId);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("StateService | findAll | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void save(StateDTO stateDTO) {
        log.debug("StateService | findAllByCountryId | state: " + stateDTO.toString());

        try {
            if (isExist(stateDTO.getCountryId(), stateDTO.getName())) {
                throw new BusinessException("State already exist", HttpStatus.BAD_REQUEST);
            }

            State state = new State(stateDTO.getName(), countryService.findById(stateDTO.getCountryId()));
            stateRepository.save(state);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("StateService | findAll | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Boolean isExist(Long countryId, String state) {
        log.debug("StateService | isExist | countryId: " + countryId);
        log.debug("StateService | isExist | state: " + state);

        try {
            return stateRepository.existsByNameAndCountryId(state, countryId);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("StateService | findAll | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public void delete(Long stateId) {
        log.debug("StateService | findAllByCountryId | stateId: " + stateId);

        try {
            stateRepository.deleteById(stateId);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("StateService | findAll | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
