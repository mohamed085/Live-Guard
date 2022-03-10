package com.liveguard.repository;

import com.liveguard.domain.Setting;
import com.liveguard.domain.SettingCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class SettingRepositoryTest {
    @Autowired
    private SettingRepository repo;

    @Test
    public void testCreateGeneralSettings() {
        Setting siteName = new Setting("SITE_NAME", "Live Guard", SettingCategory.GENERAL);
        Setting copyright = new Setting("COPYRIGHT", "Copyright (C) Live Guard Ltd.", SettingCategory.GENERAL);

        repo.saveAll(List.of(siteName, copyright));

        Iterable<Setting> iterable = repo.findAll();

        assertThat(iterable).size().isGreaterThan(0);
    }

    @Test
    public void testCreateCurrencySettings() {
        Setting currencyId = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
        Setting symbolPosition = new Setting("CURRENCY_SYMBOL_POSITION", "Before price", SettingCategory.CURRENCY);
        Setting decimalPointType = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
        Setting decimalDigits = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
        Setting thousandsPointType = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.CURRENCY);

        repo.saveAll(List.of(currencyId, symbolPosition, decimalPointType,
                decimalDigits, thousandsPointType));

    }

    @Test
    public void testListSettingsByCategory() {
        List<Setting> settings = repo.findByCategory(SettingCategory.GENERAL);

        settings.forEach(System.out::println);
    }

}