package main.repositories;

import main.model.PostCalendarResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCalendarResponseRepository extends JpaRepository<PostCalendarResponse,Integer> {
}
