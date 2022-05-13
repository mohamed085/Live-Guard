package com.liveguard.service.serviceImp;

import com.liveguard.domain.Chip;
import com.liveguard.domain.ChipVersion;
import com.liveguard.dto.ChipDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.ChipRepository;
import com.liveguard.service.ChipService;
import com.liveguard.service.ChipVersionService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ChipServiceImp implements ChipService {

    public static final int CHIPS_PER_PAGE = 6;

    private final ChipRepository chipRepository;
    private final ChipVersionService chipVersionService;

    public ChipServiceImp(ChipRepository chipRepository, ChipVersionService chipVersionService) {
        this.chipRepository = chipRepository;
        this.chipVersionService = chipVersionService;
    }

    @Override
    public Boolean existsByKey(String key) {
        log.debug("ChipService | existsByKey | key: " + key);
        return chipRepository.existsByKey(key);
    }

    @Override
    public void save(ChipDTO chipDTO) {
        log.debug("ChipService | save | key: " + chipDTO);

        if (existsByKey(chipDTO.getKey())) {
            throw new BusinessException("Chip key already exist", HttpStatus.BAD_REQUEST);
        }

        ChipVersion chipVersion = chipVersionService.findById(chipDTO.getChipVersionId());
        log.debug("ChipService | save | chipVersion: " + chipVersion.toString());

        Chip chip = new Chip();
        chip.setChipVersion(chipVersion);
        chip.setKey(chipDTO.getKey());
        chip.setPassword(RandomStringUtils.randomAlphanumeric(9));
        chip.setUsed(false);

        chipRepository.save(chip);
    }

    @Override
    public Chip findById(Long id) {
        log.debug("ChipService | findById | id: " + id);
        return chipRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Chip not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Chip> findAll() {
        log.debug("ChipService | findAll");

        return (List<Chip>) chipRepository.findAll();
    }

    @Override
    public Page<Chip> findAllByPage(int pageNum, String sortField, String sortDir, String keyword) {
        log.debug("ChipService | findAllByPage");
        log.debug("ChipService | findAllByPage | pageNum: " + pageNum);
        log.debug("ChipService | findAllByPage | sortField: " + sortField);
        log.debug("ChipService | findAllByPage | sortDir: " + sortDir);
        log.debug("ChipService | findAllByPage | keyword: " + keyword);

        if (sortField == null) {
            sortField = "id";
        }

        if (sortDir == null) {
            sortDir = "asc";
        }

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("disc") ? sort.descending() : sort.ascending();

        Pageable pageable = PageRequest.of(pageNum - 1 , CHIPS_PER_PAGE, sort);


        if (keyword == null) {
            return chipRepository.findAll(pageable);
        } else {
            return chipRepository.findAll(keyword, pageable);
        }
    }

    @Override
    public void deleteById(Long id) {
        log.debug("ChipService | deleteById | id: " + id);

        if (chipRepository.existsByIdAndUsed(id, true)) {
            log.error("ChipService | deleteById | This chip already used");
            throw new BusinessException("This chip already used", HttpStatus.BAD_REQUEST);
        }
        chipRepository.deleteById(id);
    }

    @Override
    public List<Chip> findAllByChipVersion(Long id) {
        log.debug("ChipService | findAllByChipVersion | id: " + id);

        return chipRepository.findAllByChipVersionId(id);
    }

    @Override
    public void updateUsedStatus(Long chipId, Boolean used) {
        log.debug("ChipService | existsByChipIdAndUserId | chipId: " + chipId);
        log.debug("ChipService | existsByChipIdAndUserId | used: " + used);

        chipRepository.updateUsedStatus(chipId, used);
    }

    @Override
    public Chip findByKeyAndPassword(String key, String password) {
        log.debug("ChipService | existsByKeyAndPassword | key: " + key);
        log.debug("ChipService | existsByKeyAndPassword | password: " + password);


        return chipRepository.findByKeyAndPassword(key, password)
                .orElseThrow(() -> new BusinessException("Key or password incorrect", HttpStatus.NOT_FOUND));
    }
}
