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

    @Query(value = "SELECT * FROM Posts posts WHERE posts.is_active = 1 " +
            "AND posts.moderation_status = 'ACCEPTED' AND posts.time <= NOW() " +
     //       "AND mode = :mode " +
            "ORDER BY posts.time DESC",
            nativeQuery = true)
    Page<Posts> findPostsByMode(Pageable pageable, @Param("mode") String mode);

    @Query(value = "SELECT * FROM Posts posts WHERE posts.is_active = 1 " +
            "AND posts.moderation_status = 'ACCEPTED' AND posts.time <= NOW() " +
            "AND title LIKE CONCAT('%','query','%') " +
            "ORDER BY posts.time DESC",
            nativeQuery = true)
    Page<Posts> findPostsByQuery(Pageable pageable, @Param("query") String query);

        @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
                "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
                "AND DATE_FORMAT(posts.time, '%Y-%m-%d') = :date " +
                "ORDER BY posts.time DESC",
                nativeQuery = true)
        Page<Posts> findPostsByDate(Pageable pageable, @Param("date") String date);

    @Query(value = "SELECT * FROM posts " +
            "JOIN tag2post ON posts.id = tag2post.post_id "+
            "JOIN tags ON tag2post.tag_id = tags.id "+
            "WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "AND tags.name = :tag " +
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


        @Query (value = "SELECT id FROM Users u WHERE u=(SELECT p.user FROM Posts p WHERE p.id=:postId)")
        int getUserIdByPostId(@Param("postId") int postId);
        @Query ("SELECT title FROM Posts posts WHERE id=:postId")
        String getTitle(@Param("postId") int postId);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND (moderation_status = 'NEW' OR moderator_id = 1) " +
            "AND time <= NOW() ORDER BY posts.time DESC",
            nativeQuery = true)
    Page<Posts> findPostsModeration(Pageable pageable);

    @Query(value = "SELECT time FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "AND DATE_FORMAT(posts.time, '%YYYY%') = :year " +
            "ORDER BY posts.time DESC",
            nativeQuery = true)
    Map<Date, Integer> сalendarDates(@Param("year") int year);

    @Query(value = "SELECT * FROM posts " +
            "JOIN post_comments ON posts.id = post_comments.post_id" +
            "WHERE posts.is_active = 1 AND posts.moderation_status = 'ACCEPTED' AND posts.time <= NOW() " +
            "ORDER BY COUNT(post_comments.post_id) DESC", nativeQuery = true)
    Page<Posts> findPopularPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "ORDER BY posts.time ASC", nativeQuery = true)
    Page<Posts> findEarlyPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts " +
            "JOIN post_votes ON posts.id = post_votes.post_id" +
            "WHERE posts.is_active = 1 AND posts.moderation_status = 'ACCEPTED' AND posts.time <= NOW() " +
            "ORDER BY COUNT(post_votes.value) DESC", nativeQuery = true)
    Page<Posts> findBestPosts(Pageable pageable);
    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "ORDER BY time DESC", nativeQuery = true)
    Page<Posts> findRecentPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND (moderation_status = 'ACCEPTED' AND moderator_id =: moderatorId) " +
            "AND time <= NOW() ORDER BY time DESC",
            nativeQuery = true)
    Page<Posts> findAcceptedPostsByModerator(Pageable pageable,@Param("moderatorId") int moderatorId);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND (moderation_status = 'DECLINED' AND moderator_id =: moderatorId) " +
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
}
