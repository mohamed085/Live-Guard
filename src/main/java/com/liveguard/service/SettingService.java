package com.liveguard.service;

import com.liveguard.domain.EmailSettingBag;
import com.liveguard.domain.Setting;
import com.liveguard.util.GeneralSettingBag;

import java.util.List;

public interface SettingService {

    EmailSettingBag getEmailSettings();

    List<Setting> getAllSettings();

    GeneralSettingBag getGeneralSettings();

    void saveAll(Iterable<Setting> settings);

    List<Setting> getMailServerSettings();

    List<Setting> getMailTemplateSettings();

    List<Setting> getCurrencySettings();
}
