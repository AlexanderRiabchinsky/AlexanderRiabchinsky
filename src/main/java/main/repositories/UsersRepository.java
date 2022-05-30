package main.repositories;

import main.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
    @Query("SELECT name FROM Users users WHERE id=:id")
    String getUserNameById(@Param("id") int id);
}

