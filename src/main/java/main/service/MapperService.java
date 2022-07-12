package main.service;

import lombok.AllArgsConstructor;
import main.api.response.PostCommentsExternal;
import main.api.response.PostExternal;
import main.api.response.UserExternal;
import main.model.PostComments;
import main.model.Posts;
import main.model.Users;
import main.repositories.PostCommentsRepository;
import main.repositories.PostVotesRepository;
import main.repositories.PostsRepository;
import main.repositories.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MapperService {

  //  @Autowired
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    private final PostCommentsRepository postCommentsRepository;
    private final PostVotesRepository postVotesRepository;


    public PostExternal convertPostToDto(Posts post) {System.out.println("post marker ");
        PostExternal postDto = new PostExternal();
        postDto.setId(post.getId());System.out.println("postId = "+post.getId());
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
        userDto.setName(user.getName());
        return userDto;
    }
    public UserExternal convertUserToCheck(Users user){
        UserExternal userCheck = new UserExternal();
        userCheck.setId(user.getId());
        userCheck.setName(user.getName());
        userCheck.setPhoto(user.getPhoto());
        userCheck.setEmail(user.getEmail());
        userCheck.setModeration(user.getIsModerator()==1);
        userCheck.setModerationCount(usersRepository.findModerationCount(user.getId()));
        userCheck.setSettings(true);//Непонятно назначение параметра
        return userCheck;
    }

    public PostCommentsExternal convertPostToComment(PostComments pc) {
        PostCommentsExternal postToComment = new PostCommentsExternal();
        postToComment.setId(pc.getId());
        postToComment.setTimestamp(pc.getTime());
        postToComment.setText(pc.getText());
        postToComment.setUser(convertUserToDto(usersRepository.findById(pc.getParentId()).get()));

        return postToComment;
    }
}
