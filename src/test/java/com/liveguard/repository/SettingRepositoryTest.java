package com.liveguard.repository;

import com.liveguard.domain.Setting;
import com.liveguard.domain.SettingCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class SettingRepositoryTest {

    @Autowired
    SettingRepository settingRepository;

    @Test
    void createGeneralSettings() {
        List<Setting> generalSettings = Arrays.asList(
                new Setting("SITE_NAME", "Live Guard", SettingCategory.GENERAL),
                new Setting("COPYRIGHT", "Copyright (C) Live Guard Ltd.", SettingCategory.GENERAL),
                new Setting("WEBSITE_LINK", "http://localhost:8080/", SettingCategory.GENERAL),
                new Setting("ANDROID_APP_LINK", "", SettingCategory.GENERAL),
                new Setting("IOS_APP_LINK", "", SettingCategory.GENERAL),
                new Setting("SLIDE_PHOTO_1", "", SettingCategory.GENERAL),
                new Setting("SLIDE_PHOTO_2", "", SettingCategory.GENERAL),
                new Setting("SLIDE_PHOTO_3", "", SettingCategory.GENERAL),
                new Setting("SLIDE_CAPTION_1", "", SettingCategory.GENERAL),
                new Setting("SLIDE_CAPTION_2", "", SettingCategory.GENERAL),
                new Setting("SLIDE_CAPTION_3", "", SettingCategory.GENERAL)
        );

        List<Setting> savedGeneralSettings = settingRepository.saveAll(generalSettings);
        System.out.println(savedGeneralSettings.toString());
        assertEquals(savedGeneralSettings.size(), generalSettings.size());

    }

    @Test
    void createEmailSettings() {
        List<Setting> emailSettings = Arrays.asList(
                new Setting("MAIL_HOST", "smtp.gmail.com", SettingCategory.MAIL_SERVER),
                new Setting("MAIL_PORT", "587", SettingCategory.MAIL_SERVER),
                new Setting("MAIL_USERNAME", "Mohamed085555@gmail.com", SettingCategory.MAIL_SERVER),
                new Setting("MAIL_PASSWORD", "", SettingCategory.MAIL_SERVER),
                new Setting("SMTP_AUTH", "true", SettingCategory.MAIL_SERVER),
                new Setting("SMTP_SECURED", "true", SettingCategory.MAIL_SERVER),
                new Setting("MAIL_FROM", "LiveGuard.company@gmail.com", SettingCategory.MAIL_SERVER),
                new Setting("MAIL_SENDER_NAME", "Live Guard Company", SettingCategory.MAIL_SERVER),
                new Setting("CUSTOMER_VERIFY_SUBJECT", "Please verify your code registration to continue.", SettingCategory.MAIL_TEMPLATES),
                new Setting("CUSTOMER_VERIFY_CONTENT", "<div>Dear [[name]],</div><div><b><br></b></div><div><b>Please verify your code registration to continue </b></div><span style=\"font-size:18px;\"><br>\n" +
                        "  <br>\n" +
                        "  <a href=\"[[url]]\" target=\"_self\">VERIFY</a><h1></h1><span style=\"font-size:24px;\"><font color=\"#0000ff\"><b></b></font></span></span><div><b><font color=\"#0000ff\"></font></b></div>", SettingCategory.MAIL_TEMPLATES)
        );

        List<Setting> savedGeneralSettings = settingRepository.saveAll(emailSettings);
        System.out.println(savedGeneralSettings.toString());
        assertEquals(savedGeneralSettings.size(), emailSettings.size());

    }

    @Test
    void findByKey() {
        Setting setting = settingRepository.findByKey("WEBSITE_LINK");
        System.out.println(setting);

        assertEquals(setting.getKey(), "WEBSITE_LINK");
    }

    @Test
    void findByCategory() {
    }

    @Test
    void findByTwoCategories() {
    }
}