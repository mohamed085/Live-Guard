package com.liveguard.service;

import com.liveguard.domain.EmailSettingBag;

public interface SettingService {

    String getWebsiteLink();
    EmailSettingBag getEmailSettings();
}
