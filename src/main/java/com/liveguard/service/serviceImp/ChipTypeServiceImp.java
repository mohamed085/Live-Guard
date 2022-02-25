package com.liveguard.service.serviceImp;

import com.liveguard.domain.ChipType;
import com.liveguard.dto.ChipTypeDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ChipTypeMapper;
import com.liveguard.repository.ChipTypeRepository;
import com.liveguard.service.ChipTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ChipTypeServiceImp implements ChipTypeService {

    private final ChipTypeRepository chipTypeRepository;

    public ChipTypeServiceImp(ChipTypeRepository chipTypeRepository) {
        this.chipTypeRepository = chipTypeRepository;
    }

    @Override
    public List<ChipType> findAll() {
        log.debug("ChipTypeService | findAll");

        return StreamSupport
                .stream(chipTypeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public ChipType findById(Long id) {
        log.debug("ChipTypeService | findById | id: " + id);

        return chipTypeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("This chip type not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public ChipType add(ChipTypeDTO chipTypeDTO) {
        log.debug("ChipTypeService | add: " + chipTypeDTO.toString());

        return chipTypeRepository.save(ChipTypeMapper.chipTypeDTOToChipType(chipTypeDTO));
    }
}
