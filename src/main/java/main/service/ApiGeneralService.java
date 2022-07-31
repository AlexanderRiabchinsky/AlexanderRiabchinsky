package main.service;

import lombok.AllArgsConstructor;
import main.api.request.ModerationRequest;
import main.api.request.ProfileRequest;
import main.api.request.RegPostRequest;
import main.api.request.SetCommentRequest;
import main.api.response.*;
import main.model.PostComments;
import main.model.PostVotes;
import main.model.Posts;
import main.model.Tags;
import main.repositories.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Math.*;
import static org.flywaydb.core.internal.util.StringUtils.leftPad;

@Service
@AllArgsConstructor
public class ApiGeneralService {

    private final TagsRepository tagsRepository;
    private final PostsRepository postsRepository;
    private final GlobalSettingsRepository globalSettingsRepository;
    private final UserRepository userRepository;
    private final PostCommentsRepository postCommentsRepository;
    private final PostVotesRepository postVotesRepository;
    private final MapperService mapperService;

    private final static int MAX_IMAGE_LENTH = 30720;
    private final static int MIN_COMMENT_LENTH = 5;

    public SettingsResponse getGlobalSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setMultiuserMode(true);
        settingsResponse.setStatisticsIsPublic(true);
        settingsResponse.setPostPremoderation(false);
        return settingsResponse;
    }

    public TagResponse getTagResponse(String query) {
        TagResponse tagResponse = new TagResponse();
        List<Tags> tags = tagsRepository.findAll();
        ArrayList<TagExternal> tagsDT = new ArrayList<>();
        //Пробуем собрать тэги содержащие query
        int weightMax = 0;
        if (query != null) {
            for (Tags tag : tags) {
                TagExternal tagExt = new TagExternal();
                tagExt.setName(tag.getName());
                int weight = tagsRepository.findPostNumber(tag.getId());
                if (weight > weightMax) {
                    weightMax = weight;
                }
                tagExt.setWeight(weight);
                if (tag.getName().contains(query)) {
                    tagsDT.add(tagExt);
                }
            }
        }
        //Если тэгов с query нет, берем все тэги
        if (tagsDT.size() == 0) {
            weightMax = 0;
            for (Tags tag : tags) {
                TagExternal tagExt = new TagExternal();
                tagExt.setName(tag.getName());
                int weight = tagsRepository.findPostNumber(tag.getId());
                if (weight > weightMax) {
                    weightMax = weight;
                }
                tagExt.setWeight(weight);
                tagsDT.add(tagExt);
            }
        }
        //Нормирование весов
        for (TagExternal tagDT : tagsDT) {
            tagDT.setWeight((double) round(tagDT.getWeight() / weightMax * 1000) / 1000);
        }
        tagResponse.setTags(tagsDT);
        return tagResponse;
    }

    public CalendarResponse getCalendar(String year) {
        CalendarResponse calendarResponse = new CalendarResponse();
        int requestYear = year == null ? LocalDateTime.now().getYear() : Integer.parseInt(year);
        String[] allYearsPost = postsRepository.findAllYearValue();
        calendarResponse.setYears(allYearsPost);
        Map<String, Integer> dateCount = new HashMap<>();

        List<String> datesByYear = postsRepository.findPostDatesByYear(String.valueOf(requestYear));
        for (String data : datesByYear) {
            dateCount.put(data, postsRepository.findPostNumberByDate(data));
        }
        calendarResponse.setPosts(dateCount);

        return calendarResponse;
    }

    public RegResponse saveImage(MultipartFile photo) throws IOException {
        RegResponse response = new RegResponse();
        String dir1 = gen(2);
        String dir2 = gen(2);
        String dir3 = gen(2);
        String newFileName = gen(5);
        String extension = FilenameUtils.getExtension(photo.getOriginalFilename());
        long fileLenth = photo.getSize();
        Map<String, String> errors = new HashMap<>();
        if (!extension.equals("jpg") && !extension.equals("png")) {
            errors.put("type", "Отправлен файл не формата изображение jpg, png");
        }
        if (fileLenth > MAX_IMAGE_LENTH) {
            errors.put("size", "Размер файла превышает допустимый размер");
        }

        if (errors.isEmpty()) {
            String dirName = "D://Others/BlogDriver/src/upload/" + dir1 + "/" + dir2 + "/" + dir3;
            newFileName = dirName + "/" + newFileName + "." + extension;
            File dir = new File(dirName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            BufferedImage bufferedImage = ImageIO.read(photo.getInputStream());
            File outputfile = new File(newFileName);
            ImageIO.write(bufferedImage, extension, outputfile);
            response.setString(newFileName);
        } else {
            response.setResult(false);
            response.setErrors(errors);
        }

        return response;
    }

    public RegResponse comment(SetCommentRequest request,
                               Principal principal) {
        RegResponse regResponse = new RegResponse();
        Map<String, String> errors = new HashMap<>();
        Optional<Posts> postOpt = postsRepository.findById(request.getPostId());
        Optional<PostComments> postCommOpt = postCommentsRepository.findById(request.getParentId());

        String text = request.getText();
        if(text.length()<MIN_COMMENT_LENTH){
            errors.put("text","Текст комментария отсутствует или менее "+MIN_COMMENT_LENTH+" символов");
        }
        if (errors.isEmpty()) {
            regResponse.setResult(true);
            PostComments pc = new PostComments();
            if (request.getParentId()>0){
                pc.setParentId(request.getParentId());
            } else pc.setParentId(null);
            pc.setPost(postOpt.get());
            pc.setUser(userRepository.findByEmail(principal.getName()).get());
            pc.setTime(LocalDateTime.now());
            pc.setText(request.getText());
            postCommentsRepository.save(pc);

            regResponse.setId(postCommentsRepository.findRecentId());
        } else {
            regResponse.setResult(false);
            regResponse.setErrors(errors);
        }
        return regResponse;
    }

    public boolean isMultiUser() {
        return globalSettingsRepository.findSettingValue("MULTIUSER_MODE").equals("YES");
    }

    public boolean isPostPremoderated() {
        return globalSettingsRepository.findSettingValue("POST_PREMODERATION").equals("YES");
    }

    public boolean ststisticIsPublic() {
        return globalSettingsRepository.findSettingValue("STATISTICS_IS_PUBLIC").equals("YES");
    }

    public static String gen(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = length; i > 0; i -= 12) {
            int n = min(12, abs(i));
            sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
        }
        return sb.toString();
    }

    public ResultResponse moderation(ModerationRequest moderationRequest,
                                     Principal principal) {
        ResultResponse response = new ResultResponse();
        int moderatorAuth = userRepository.findByEmail(principal.getName()).get().getIsModerator();
        int moderatorId = userRepository.findByEmail(principal.getName()).get().getId();
        Optional<Posts> postToModerate = postsRepository.findById(moderationRequest.getPost_id());
        if (!postToModerate.isPresent() || (!moderationRequest.getDecision().equals("accept")
                && !moderationRequest.getDecision().equals("decline")) || (moderatorAuth != 1)) {
            response.setResult(false);
        } else {
            Posts post = postToModerate.get();
            if (moderationRequest.getDecision().equals("accept")) {
                post.setStatus(ModerationStatus.ACCEPTED);
            }
            if (moderationRequest.getDecision().equals("declined")) {
                post.setStatus(ModerationStatus.DECLINED);
            }
            post.setModeratorId(moderatorId);
            postsRepository.save(post);
            response.setResult(true);
        }

        return response;
    }

    public RegResponse profile(MultipartFile photo, ProfileRequest request, Principal principal) throws IOException {
        RegResponse response = new RegResponse();

        return response;
    }
    public ResultResponse checkComment(SetCommentRequest request){
        ResultResponse response = new ResultResponse();
        response.setResult(true);
        Optional<Posts> postOpt = postsRepository.findById(request.getPostId());
        Optional<PostComments> postCommOpt = postCommentsRepository.findById(request.getParentId());
        if(!postOpt.isPresent()){response.setResult(false);}
        if(!postCommOpt.isPresent()&&(request.getParentId()!=0)){response.setResult(false);}
        return response;
    }
    public RegResponse getErrorResponse(SetCommentRequest request){
        Map<String, String> errors = new HashMap<>();
        RegResponse response = new RegResponse();
        Optional<Posts> postOpt = postsRepository.findById(request.getPostId());
        Optional<PostComments> postCommOpt = postCommentsRepository.findById(request.getParentId());
        if(!postOpt.isPresent()){ errors.put("post","Пост отсутствует");}
        if(!postCommOpt.isPresent()){ errors.put("comment","Комментарий отсутствует");}
        response.setResult(false);
        response.setErrors(errors);
        return response;
    }

    public StatisticsResponse statisticsMy(Principal principal){
        int likes = 0;
        int dislikes = 0;
        int views = 0;
        long first = mapperService.getTimestampFromLocalDateTime(LocalDateTime.now());
        List<Posts> posts = postsRepository.findMyActivePosts(userRepository.findByEmail(principal.getName()).get().getId());
        List<PostVotes> postVotes = postVotesRepository.findAll();
        for(Posts post:posts){
            for (PostVotes pv:postVotes){
                if(post.getId()==pv.getPost().getId() && pv.getValue()==1) {likes++;}
                if(post.getId()==pv.getPost().getId() && pv.getValue()==-1) {dislikes++;}
            }
            views+=post.getViewCount();
            first=Math.min(first,mapperService.getTimestampFromLocalDateTime(post.getTimestamp()));
        }
        StatisticsResponse response = new StatisticsResponse();
        response.setPostsCount(posts.stream().count());
        response.setLikesCount(likes);
        response.setDislikesCount(dislikes);
        response.setViewsCount(views);
        response.setFirstPublication(response.getPostsCount()==0? 0:first);

        return response;
    }

    public StatisticsResponse statisticsAll(){
        int likes = 0;
        int dislikes = 0;
        int views = 0;
        long first = mapperService.getTimestampFromLocalDateTime(LocalDateTime.now());
        List<Posts> posts = postsRepository.findAll();
        List<PostVotes> postVotes = postVotesRepository.findAll();
        for(Posts post:posts){
            for (PostVotes pv:postVotes){
                if(post.getId()==pv.getPost().getId() && pv.getValue()==1) {likes++;}
                if(post.getId()==pv.getPost().getId() && pv.getValue()==-1) {dislikes++;}
            }
            views+=post.getViewCount();
            first=Math.min(first,mapperService.getTimestampFromLocalDateTime(post.getTimestamp()));
        }
        StatisticsResponse response = new StatisticsResponse();
        response.setPostsCount(posts.stream().count());
        response.setLikesCount(likes);
        response.setDislikesCount(dislikes);
        response.setViewsCount(views);
        response.setFirstPublication(first);

        return response;
    }

    public ResultResponse settings(SettingsResponse request,
                         Principal principal) {
        ResultResponse response = new ResultResponse();


        return  response;
    }
}
