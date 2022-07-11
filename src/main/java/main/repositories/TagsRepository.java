package main.repositories;

import main.model.Posts;
import main.model.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tags,Integer> {
    @Query(value = "SELECT name FROM tags " +
            "JOIN tag2post ON tags.id = tag2post.tag_id "+
            "JOIN posts ON tag2post.post_id = posts.id "+
            "WHERE posts.is_active = 1 " +
            "AND posts.moderation_status = 'ACCEPTED' AND posts.time <= NOW() " +
            "AND posts.id = :postId ",
            nativeQuery = true)
    List<String> findTagsByPost(@Param("postId") int postId);
    @Query(value = "SELECT COUNT(post_id) FROM tag2post " +
            "WHERE tag2post.tag_id = :tagId",
            nativeQuery = true)
    int findPostNumber(@Param("tagId") int tagId);
}
