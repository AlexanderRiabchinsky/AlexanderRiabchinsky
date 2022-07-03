package main.repositories;

import main.model.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostComments,Integer> {
//    @Query("SELECT COUNT(*) FROM post_comments WHERE post = postId")
//    int findPostCommentsCount(int id);
}
