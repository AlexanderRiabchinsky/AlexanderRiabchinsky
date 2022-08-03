package main.repositories;

import main.model.PostVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes,Integer> {

    @Query(value ="SELECT * FROM post_votes WHERE post_id =:postId and user_id=:userId",
            nativeQuery = true)
    Optional<PostVotes>checkVote(@Param("postId") int PostId,@Param("userId") int userId);
}
