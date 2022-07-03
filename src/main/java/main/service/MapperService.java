package main.service;

import main.api.response.PostExternal;
import main.api.response.PostExternal2;
import main.api.response.PostResponse;
import main.api.response.UserExternal;
import main.model.Posts;
import main.model.Users;
import main.repositories.PostCommentsRepository;
import main.repositories.PostVotesRepository;
import main.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    @Autowired
    private  PostsRepository postsRepository;
    private PostCommentsRepository postCommentsRepository;
    private PostVotesRepository postVotesRepository;

    public PostExternal convertPostToDto(Posts post) {
        PostExternal postDto = new PostExternal();
        postDto.setId(post.getId());
        postDto.setActive(post.getIsActive() == 1); //новый метод в PostExternal2
        postDto.setTimestamp(post.getTimestamp()/*post.getTime().getTime() / 1000*/);// непонятная конструкция post.getTime().getTime() / 1000
        postDto.setUser(convertUserToDto(post.getUser()));//?
        postDto.setTitle(post.getTitle());
        postDto.setText(post.getText());
        String postDtoText = postDto.getText()
                .replaceAll("<(/)?([0-9A-Za-z\\-;:./=\"\\s]+)?>", "")
                .replaceAll(" ", "");
        postDto.setAnnounce(postDtoText.length() < 150 ? postDtoText : postDtoText.substring(0, 150) + "...");
        postDto.setLikeCount(postsRepository.findPostLikesCount(post.getId()));//?
        postDto.setDislikeCount(postsRepository.findPostDislikesCount(post.getId()));//?
        postDto.setCommentCount(postsRepository.findPostCommentsCount(post.getId()));//?
        postDto.setViewCount(post.getViewCount());
        return postDto;
    }
    public UserExternal convertUserToDto(Users user){
        UserExternal userDto = new UserExternal();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }
}
