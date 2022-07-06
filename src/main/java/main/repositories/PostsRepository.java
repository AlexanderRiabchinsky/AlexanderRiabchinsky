package main.repositories;

import main.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Integer> {

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "AND mode = :mode " +
            "ORDER BY posts.time DESC",
            nativeQuery = true)
    Page<Posts> findPostsByMode(Pageable pageable, @Param("mode") String mode);

        @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
                "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
                "AND DATE_FORMAT(posts.time, '%Y-%m-%d') = :date " +
                "ORDER BY posts.time DESC",
                nativeQuery = true)
        Page<Posts> findPostsByDate(Pageable pageable, @Param("date") String date);

//        @Query("SELECT COUNT(value) FROM PostVotes pv WHERE pv.post =:postId and pv.value = 1")
        @Query(value = "SELECT COUNT(post_votes.id) FROM posts " +
        "JOIN post_votes ON posts.id = post_votes.post_id WHERE posts.id = :postId " +
        "AND posts.is_active = 1 AND posts.moderation_status = 'ACCEPTED' " +
        "AND posts.time <= NOW() AND post_votes.value = 1",
        nativeQuery = true)
        int findPostLikesCount(@Param("postId") int postId);

//        @Query("SELECT COUNT(value) FROM PostVotes pv WHERE pv.post =:postId and pv.value = -1")
        @Query(value = "SELECT COUNT(post_votes.id) FROM posts " +
        "JOIN post_votes ON posts.id = post_votes.post_id WHERE posts.id = :postId " +
        "AND posts.is_active = 1 AND posts.moderation_status = 'ACCEPTED' " +
        "AND posts.time <= NOW() AND post_votes.value = -1",
        nativeQuery = true)
        int findPostDislikesCount(@Param("postId") int postId);

 //       @Query("SELECT COUNT(post) FROM PostComments pc WHERE pc.post =:postId")
        @Query(value = "SELECT COUNT(post_comments.id) FROM posts " +
         "JOIN post_comments ON posts.id = post_comments.post_id WHERE posts.id = :postId " +
         "AND posts.is_active = 1 AND posts.moderation_status = 'ACCEPTED' " + "AND posts.time <= NOW()",
         nativeQuery = true)
        int findPostCommentsCount(@Param("postId") int postId);


        @Query (value = "SELECT id FROM Users u WHERE u=(SELECT p.user FROM Posts p WHERE p.id=:postId)")
  //      @Query (value = "SELECT id FROM Users u LEFT OUTER JOIN Posts p on u.id=p.user_id  WHERE p.id=postId")
  //      @Query (value = "SELECT id FROM Users u LEFT OUTER JOIN Posts p on u=p.user  WHERE p.id=postId")
  //      @Query (value = "SELECT id FROM Users u LEFT OUTER JOIN Posts p on u.id=p.user.id  WHERE p.id=postId")
   //     @Query (value = "SELECT  Posts p.user.id  WHERE p.id=postId")
        int getUserIdByPostId(@Param("postId") int postId);
        @Query ("SELECT title FROM Posts posts WHERE id=:postId")
        String getTitle(@Param("postId") int postId);

//        @Query("SELECT p FROM Posts p " +
//                "LEFT JOIN Users u ON u.id = p.user.id " +
//                "LEFT JOIN PostComments pc ON pc.post.id = p.id " +
//                "LEFT JOIN PostVotes pvl on pvl.post.id = p.id and pvl.value = 1 " +
//                "WHERE p.isActive = 1 AND p.enum.valueOf(status) = 'ACCEPTED' AND p.time <= CURRENT_DATE() " +
//                "GROUP BY p.id ORDER BY COUNT(pvl) DESC"
//        )
//        Page<Posts> findPostsOrderByLikes(Pageable pageable);
}
