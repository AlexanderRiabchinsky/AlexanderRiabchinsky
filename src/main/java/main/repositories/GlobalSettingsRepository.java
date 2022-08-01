package main.repositories;

import main.model.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings,Integer> {
    @Query(value = "SELECT value FROM global_settings WHERE code =:code",
            nativeQuery = true)
    String findSettingValue(@Param("code") String code);
    @Modifying
    @Query(value = "UPDATE global_settings SET value=:value WHERE code =:code",
            nativeQuery = true)
    void setSettingValue(@Param("code") String code, @Param("value") String value);
}
