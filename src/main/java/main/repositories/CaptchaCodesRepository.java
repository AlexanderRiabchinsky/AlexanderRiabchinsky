package main.repositories;

import main.model.CaptchaCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface CaptchaCodesRepository extends JpaRepository<CaptchaCodes,Integer> {
    @Query("SELECT code FROM CaptchaCodes cc WHERE cc.secretCode =:secret")
    Optional<CaptchaCodes> findCaptchaBySecretCode(@Param("secret") String secret);
    @Query(value = "INSERT INTO CaptchaCodes(time,code,secret_code) VALUES(date,code,secret)",nativeQuery = true)
    Optional<CaptchaCodes> regNewCaptcha(@Param("date") Date date, @Param("code")String code, @Param("secret") String secret);
}