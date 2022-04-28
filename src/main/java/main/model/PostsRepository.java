package main.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Integer> {
//    default int getUser(int postId){
//        int userId;
        @Query ("SELECT user_id FROM Posts posts WHERE id=:postId")
        Integer getUser(@Param("postId") int postId);
        @Query ("SELECT title FROM Posts posts WHERE id=:postId")
        String getTitle(@Param("postId") int postId);
        }
//}
