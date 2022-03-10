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
        return settingRepository.findAll();
    }

    @Override
    public GeneralSettingBag getGeneralSettings() {
        log.debug("SettingService | getGeneralSettings");
        List<Setting> settings = new ArrayList<>();

        List<Setting> generalSettings = settingRepository.findByCategory(SettingCategory.GENERAL);
        List<Setting> currencySettings = settingRepository.findByCategory(SettingCategory.CURRENCY);

        settings.addAll(generalSettings);
        settings.addAll(currencySettings);

        return new GeneralSettingBag(settings);
    }

    @Override
    public void saveAll(Iterable<Setting> settings) {
        log.debug("SettingService | saveAll");
        settingRepository.saveAll(settings);
    }

    @Override
    public List<Setting> getMailServerSettings() {
        log.debug("SettingService | getMailServerSettings");
        return settingRepository.findByCategory(SettingCategory.MAIL_SERVER);
    }

    @Override
    public List<Setting> getMailTemplateSettings() {
        log.debug("SettingService | getMailTemplateSettings");
        return settingRepository.findByCategory(SettingCategory.MAIL_TEMPLATES);
    }

    @Override
    public List<Setting> getCurrencySettings() {
        log.debug("SettingService | getCurrencySettings");
        return settingRepository.findByCategory(SettingCategory.CURRENCY);
    }
}
