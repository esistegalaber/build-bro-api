package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Setting;
import org.springframework.data.repository.CrudRepository;

public interface SettingRepository extends CrudRepository<Setting, String> {
}
