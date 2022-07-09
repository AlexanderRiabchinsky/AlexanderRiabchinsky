package main.repositories;

import main.model.CaptchaCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CaptchaCodesRepository extends JpaRepository<CaptchaCodes,Integer> {
    @Query(value = "SELECT * FROM captcha_codes WHERE secret_code = :secret_code " +
            "AND time > (NOW() - INTERVAL 1 HOUR)", nativeQuery = true)
    Optional<CaptchaCodes> findCaptchaBySecretCode(@Param("secret_code") String secretCode);
    @Query(value = "SELECT * FROM captcha_codes WHERE time < (NOW() - INTERVAL 1 HOUR)", nativeQuery = true)
    List<CaptchaCodes> findOldCaptchas();
}