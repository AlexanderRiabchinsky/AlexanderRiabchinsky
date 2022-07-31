package main.repositories;

import main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    @Query("select u.name from User u where u.id =:id")
    String findNameById(@Param("id") int id);

    @Query("select u.photo from User u where u.id =:id")
    String findPhotoById(@Param("id") int id);

    @Query("select u.email from User u where u.id =:id")
    String findEmailById(@Param("id") int id);

    @Query("select u.is_moderator from User u where u.email =:email")
    int findIfModerator(@Param("email") String email);
}

