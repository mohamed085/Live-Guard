package com.liveguard.service.serviceImp;

import com.liveguard.domain.EmailSettingBag;
import com.liveguard.domain.Setting;
import com.liveguard.domain.SettingCategory;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.SettingRepository;
import com.liveguard.service.SettingService;
import com.liveguard.util.GeneralSettingBag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SettingServiceImp implements SettingService {

    private final SettingRepository settingRepository;

    public SettingServiceImp(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public EmailSettingBag getEmailSettings() {
        log.debug("SettingService | getEmailSettings");
        try {
            List<Setting> settings = settingRepository.findByCategory(SettingCategory.MAIL_SERVER);
            settings.addAll(settingRepository.findByCategory(SettingCategory.MAIL_TEMPLATES));

            return new EmailSettingBag(settings);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<Setting> getAllSettings() {
        log.debug("SettingService | getAllSettings");
        try {
            return settingRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GeneralSettingBag getGeneralSettings() {
        log.debug("SettingService | getGeneralSettings");
        List<Setting> settings = new ArrayList<>();

        try {
            List<Setting> generalSettings = settingRepository.findByCategory(SettingCategory.GENERAL);
            List<Setting> currencySettings = settingRepository.findByCategory(SettingCategory.CURRENCY);

            settings.addAll(generalSettings);
            settings.addAll(currencySettings);

            return new GeneralSettingBag(settings);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void saveAll(Iterable<Setting> settings) {
        log.debug("SettingService | saveAll");
        try {
            settingRepository.saveAll(settings);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Setting> getMailServerSettings() {
        log.debug("SettingService | getMailServerSettings");
        try {
            return settingRepository.findByCategory(SettingCategory.MAIL_SERVER);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Setting> getMailTemplateSettings() {
        log.debug("SettingService | getMailTemplateSettings");
        try {
            return settingRepository.findByCategory(SettingCategory.MAIL_TEMPLATES);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Setting> getCurrencySettings() {
        log.debug("SettingService | getCurrencySettings");
        try {
            return settingRepository.findByCategory(SettingCategory.CURRENCY);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
