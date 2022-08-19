package main.repositories;

import main.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Integer> {

    @Query(value = "SELECT COUNT(post_id) FROM tag2post " +
            "WHERE tag2post.tag_id = :tagId",
            nativeQuery = true)
    int findPostNumber(@Param("tagId") int tagId);

    @Query(value = "SELECT * FROM tags WHERE name = :name",
            nativeQuery = true)
    Tags findTagByName(@Param("name") String t);

}
