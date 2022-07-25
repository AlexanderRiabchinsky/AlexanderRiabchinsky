package main.repositories;

import main.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Integer> {

    @Query(value = "SELECT * FROM  posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
     //       "AND mode = :mode " +
            "ORDER BY time DESC",
            nativeQuery = true)
    Page<Posts> findPostsByMode(Pageable pageable, @Param("mode") String mode);

    @Query(value = "SELECT * FROM  posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "AND title LIKE CONCAT('%',:query,'%') " +
            "ORDER BY time DESC",
            nativeQuery = true)
    Page<Posts> findPostsByQuery(Pageable pageable, @Param("query") String query);

    @Query(value = "SELECT DISTINCT DATE_FORMAT(posts.time, '%Y') FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "GROUP BY DATE_FORMAT(posts.time, '%Y') ORDER BY DATE_FORMAT(posts.time, '%Y')",
            nativeQuery = true)
    String[] findAllYearValue();

        @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
                "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
                "AND DATE_FORMAT(posts.time, '%Y-%m-%d') = :date " +
                "ORDER BY time DESC",
                nativeQuery = true)
        Page<Posts> findPostsByDate(Pageable pageable, @Param("date") String date);

    @Query(value = "SELECT DISTINCT DATE_FORMAT(time, '%Y-%m-%d') FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "AND DATE_FORMAT(posts.time, '%Y') = :year " +
            "ORDER BY time",
            nativeQuery = true)
    List<String> findPostDatesByYear(@Param("year") String year);

    @Query(value = "SELECT COUNT(*) FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "AND DATE_FORMAT(posts.time, '%Y-%m-%d') = :date ",
            nativeQuery = true)
    int findPostNumberByDate(@Param("date") String date);

    @Query(value = "SELECT * FROM posts " +
            "JOIN tag2post ON posts.id = tag2post.post_id "+
            "JOIN tags ON tag2post.tag_id = tags.id "+
            "WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "AND tags.name =:tag " +
            "ORDER BY posts.time DESC",
            nativeQuery = true)
    Page<Posts> findPostsByTag(Pageable pageable,@Param("tag") String tag);

        @Query(value = "SELECT COUNT(post_votes.id) FROM post_votes " +
        "JOIN posts ON posts.id = post_votes.post_id WHERE posts.id = :postId " +
        "AND posts.is_active = 1 AND posts.moderation_status = 'ACCEPTED' " +
        "AND posts.time <= NOW() AND post_votes.value = 1",
        nativeQuery = true)
        int findPostLikesCount(@Param("postId") int postId);

        @Query(value = "SELECT COUNT(post_votes.id) FROM post_votes " +
        "JOIN posts ON posts.id = post_votes.post_id WHERE posts.id = :postId " +
        "AND posts.is_active = 1 AND posts.moderation_status = 'ACCEPTED' " +
        "AND posts.time <= NOW() AND post_votes.value = -1",
        nativeQuery = true)
        int findPostDislikesCount(@Param("postId") int postId);

 //       @Query("SELECT COUNT(post) FROM PostComments pc WHERE pc.post =:postId")
        @Query(value = "SELECT COUNT(post_comments.id) FROM post_comments " +
         "JOIN posts ON posts.id = post_comments.post_id WHERE posts.id = :postId " +
         "AND posts.is_active = 1 AND posts.moderation_status = 'ACCEPTED' AND posts.time <= NOW()",
         nativeQuery = true)
        int findPostCommentsCount(@Param("postId") int postId);


        @Query (value = "SELECT id FROM User u WHERE u=(SELECT p.user FROM Posts p WHERE p.id=:postId)")
        int getUserIdByPostId(@Param("postId") int postId);

        @Query ("SELECT title FROM Posts posts WHERE id=:postId")
        String getTitle(@Param("postId") int postId);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND (moderation_status = 'NEW' OR moderator_id = 1) " +
            "AND time <= NOW() ORDER BY time DESC",
            nativeQuery = true)
    Page<Posts> findPostsModeration(Pageable pageable);


    @Query(value = "SELECT * FROM posts p LEFT JOIN post_comments pc ON pc.post_id = p.id " +
        "WHERE is_active = 1 AND p.moderation_status = 'ACCEPTED' " +
        "AND p.time <= NOW() GROUP BY p.id ORDER BY COUNT(pc.post_id) DESC", nativeQuery = true)
    Page<Posts> findPopularPosts(Pageable pageable);


    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "ORDER BY time ASC", nativeQuery = true)
    Page<Posts> findEarlyPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts p LEFT JOIN post_votes pv ON pv.post_id = p.id " +
            "WHERE is_active = 1 AND p.moderation_status = 'ACCEPTED' " +
            "AND p.time <= NOW() GROUP BY p.id ORDER BY COUNT(pv.post_id) DESC", nativeQuery = true)
    Page<Posts> findBestPosts(Pageable pageable);
    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "ORDER BY time DESC", nativeQuery = true)
    Page<Posts> findRecentPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND (moderation_status = 'ACCEPTED' AND moderator_id =:moderatorId) " +
            "AND time <= NOW() ORDER BY time DESC",
            nativeQuery = true)
    Page<Posts> findAcceptedPostsByModerator(Pageable pageable,@Param("moderatorId") int moderatorId);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND (moderation_status = 'DECLINED' AND moderator_id =:moderatorId) " +
            "AND time <= NOW() ORDER BY time DESC",
            nativeQuery = true)
    Page<Posts> findDeclinedPostsByModerator(Pageable pageable,@Param("moderatorId") int moderatorId);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND (moderation_status = 'NEW') " +
            "AND time <= NOW() ORDER BY time DESC",
            nativeQuery = true)
    Page<Posts> findNewPosts(Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM posts " +
            "WHERE moderation_status = 'NEW' AND is_active = 1",nativeQuery = true)
    int findUnmoderatedPostsCount();

    @Query(value = "SELECT * FROM posts WHERE is_active = 0 " +
            "AND user_id =:myId", nativeQuery = true)
    Page<Posts> findMyInactivePosts(Pageable pageable,@Param("myId") int myId);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND (moderation_status = 'NEW' AND user_id =:myId) " +
            "AND time <= NOW() ORDER BY time DESC",
            nativeQuery = true)
    Page<Posts> findMyPendingPosts(Pageable pageable,@Param("myId") int myId);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND (moderation_status = 'DECLINED' AND user_id =:myId) " +
            "AND time <= NOW() ORDER BY time DESC",
            nativeQuery = true)
    Page<Posts> findMyDeclinedPosts(Pageable pageable,@Param("myId") int myId);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND (moderation_status = 'ACCEPTED' AND user_id =:myId) " +
            "AND time <= NOW() ORDER BY time DESC",
            nativeQuery = true)
    Page<Posts> findMyPublishedPosts(Pageable pageable,@Param("myId") int myId);
}
