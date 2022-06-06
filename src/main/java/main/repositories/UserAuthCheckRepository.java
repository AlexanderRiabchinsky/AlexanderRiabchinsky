package main.repositories;

import main.model.UserAuthCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthCheckRepository extends JpaRepository<UserAuthCheck,Integer> {
    @Query("select u.name from Users u where u.id = ?1")
    String findNameById(Integer integer);

    @Query("select u.photo from Users u where u.id = ?1")
    String findPhotoById(Integer integer);

    @Query("select u.email from Users u where u.id = ?1")
    String findEmailById(Integer integer);

//    @Query("select u.moderation from Users u where u.id = ?1")
//    boolean findModerationById(Integer integer);
//    @Query("select u.moderationCount from Users u where u.id = ?1")
//    Integer findModerationCountById(Integer integer);
//    @Query("select u.settings from Users u where u.id = ?1")
//    boolean findSettingsById(Integer integer);
}
