package main.repositories;

import main.model.PostVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes,Integer> {
//    @Query("SELECT COUNT(value) FROM post_votes WHERE post.id = postId and value = 1")
//    int findPostLikesCount(int postId);
//
//    @Query("SELECT COUNT(value) FROM post_votes WHERE post.id = postId and value = -1")
//    int findPostDislikesCount(int id);
}
