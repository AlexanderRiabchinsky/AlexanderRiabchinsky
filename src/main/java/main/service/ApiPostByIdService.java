package main.service;

import main.api.response.PostByTagResponse;
import main.api.response.PostExternal;
import main.api.response.PostIDResponse;
import main.model.Posts;
import main.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiPostByIdService {
    @Autowired
    private PostsRepository postsRepository;
    public PostIDResponse getPostByIdResponse(int id) {
        PostIDResponse postIdResponse = new PostIDResponse();
        postIdResponse.setId(id);
        postIdResponse.setTimestamp(postIdResponse.getTimestamp());
        postIdResponse.setActive(postIdResponse.isActive());
        postIdResponse.setUser(postIdResponse.getUser());
        postIdResponse.setTitle(postIdResponse.getTitle());
        postIdResponse.setText(postIdResponse.getText());
        postIdResponse.setLikeCount(postIdResponse.getLikeCount());
        postIdResponse.setDislikeCount(postIdResponse.getDislikeCount());
        postIdResponse.setViewCount(postIdResponse.getViewCount());
        postIdResponse.setComments(postIdResponse.getComments());
        postIdResponse.setTags(postIdResponse.getTags());

        return postIdResponse;
    }
}
