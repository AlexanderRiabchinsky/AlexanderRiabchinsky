package main.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Tag2PostRepository extends JpaRepository<Tag2Post,Integer> {
}
