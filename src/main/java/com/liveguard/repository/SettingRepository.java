package com.liveguard.repository;

import com.liveguard.domain.Setting;
import com.liveguard.domain.SettingCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingRepository extends CrudRepository<Setting, String> {

    List<Setting> findByCategory(SettingCategory category);

}
