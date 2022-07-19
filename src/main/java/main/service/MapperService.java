package main.service;

import lombok.AllArgsConstructor;
import main.api.response.PostCommentsExternal;
import main.api.response.PostExternal;
import main.api.response.UserExternal;
import main.model.PostComments;
import main.model.Posts;
import main.model.Users;
import main.repositories.PostsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@AllArgsConstructor
public class MapperService {
    private final PostsRepository postsRepository;


    public PostExternal convertPostToDto(Posts post) {
        PostExternal postDto = new PostExternal();
        postDto.setId(post.getId());
        postDto.setActive(post.getIsActive() == 1);
        postDto.setTimestamp(post.getTimestamp()/*post.getTime().getTime() / 1000*/);// непонятная конструкция post.getTime().getTime() / 1000
        postDto.setUser(convertUserToDto(post.getUser()));
        postDto.setTitle(post.getTitle());
        postDto.setAnnounce(post.getText());
        String postDtoText = postDto.getAnnounce()
                .replaceAll("<(/)?([0-9A-Za-z\\-;:./=\"\\s]+)?>", "")
                .replaceAll(" ", "");
        postDto.setAnnounce(postDtoText.length() < 150 ? postDtoText : postDtoText.substring(0, 150) + "...");
        postDto.setLikeCount(postsRepository.findPostLikesCount(post.getId()));
        postDto.setDislikeCount(postsRepository.findPostDislikesCount(post.getId()));
        postDto.setCommentCount(postsRepository.findPostCommentsCount(post.getId()));
        postDto.setViewCount(post.getViewCount());
        return postDto;
    }

    public UserExternal convertUserToDto(Users user){
        UserExternal userDto = new UserExternal();
        userDto.setId(user.getId());
        userDto.setRegTime(user.getRegTime());
        userDto.setPassword(user.getPassword());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoto(user.getPhoto());
        userDto.setModeration(user.getIsModerator() == 1);
        if (userDto.isModeration()) {
            userDto.setModerationCount(postsRepository.findUnmoderatedPostsCount());
            userDto.setSettings(true);
        } else {
            userDto.setModerationCount(0);
            userDto.setSettings(false);
        }
        return userDto;
    }

    public PostCommentsExternal convertCommentToDto(PostComments pc) {
        PostCommentsExternal postToComment = new PostCommentsExternal();
        postToComment.setId(pc.getId());
        postToComment.setTimestamp(pc.getTime());
        postToComment.setText(pc.getText());
        postToComment.setUser(convertUserToDto(pc.getUser()));

        return postToComment;
    }
    public long getTimestampFromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? 0 : localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }
}
