package main.repositories;

import main.model.PostComments;
import main.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostComments,Integer> {
//    @Query("SELECT COUNT(*) FROM post_comments WHERE post = postId")
//    int findPostCommentsCount(int id);

    @Query(value = "SELECT * FROM PostComments pc WHERE post_id=:postId",
            nativeQuery = true)
    Page<PostComments> findCommentsByPostId(Pageable pageable,@Param("postId") int postId);
}
