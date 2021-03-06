package main.repositories;

import main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
//    @Query(value = "SELECT COUNT(post.id) FROM posts " +
//            "WHERE user =:id " +
//            "AND posts.is_active = 1 AND posts.moderation_status = 'NEW' " + "AND posts.time <= NOW()",
//            nativeQuery = true)
//    long findModerationCount(@Param("id") int id);
//    @Query("SELECT name FROM Users users WHERE id=:id")
//    String getUserNameById(@Param("id") int id);
    Optional<User> findByEmail(String email);

    @Query("select u.name from User u where u.id =:id")
    String findNameById(@Param("id") int id);

    @Query("select u.photo from User u where u.id =:id")
    String findPhotoById(@Param("id") int id);

    @Query("select u.email from User u where u.id =:id")
    String findEmailById(@Param("id") int id);
}

