package com.liveguard.service.serviceImp;

import com.liveguard.domain.EmailSettingBag;
import com.liveguard.domain.Setting;
import com.liveguard.domain.SettingCategory;
import com.liveguard.repository.SettingRepository;
import com.liveguard.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        List<Setting> settings = settingRepository.findByCategory(SettingCategory.MAIL_SERVER);

        settings.addAll(settingRepository.findByCategory(SettingCategory.MAIL_TEMPLATES));

        return new EmailSettingBag(settings);
    }
}
