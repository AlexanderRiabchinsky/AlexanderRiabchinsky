package main.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Integer> {

        @Query ("SELECT user_id FROM Posts posts WHERE id=:postId")
        Integer getUser(@Param("postId") int postId);
        @Query ("SELECT title FROM Posts posts WHERE id=:postId")
        String getTitle(@Param("postId") int postId);

        @Query("SELECT p FROM Posts p " +
                "LEFT JOIN Users u ON u.id = p.user_id " +
                "LEFT JOIN PostComments pc ON pc.post_comment = p.id " +
                "LEFT JOIN PostVotes pvl on pvl.post_vote = p.id and pvl.value = 1 " +
                "WHERE p.is_active = 1 AND p.enum.valueOf(status) = 'ACCEPTED' AND p.time <= CURRENT_DATE() " +
                "GROUP BY p.id ORDER BY COUNT(pvl) DESC"
        )
        Page<Posts> findPostsOrderByLikes(Pageable pageable);
}
