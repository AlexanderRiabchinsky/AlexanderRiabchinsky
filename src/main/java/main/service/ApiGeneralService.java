package main.service;

import lombok.AllArgsConstructor;
import main.api.request.ModerationRequest;
import main.api.request.ProfileRequest;
import main.api.request.SetCommentRequest;
import main.api.response.*;
import main.model.*;
import main.repositories.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
    public static final int MAX_LENGTH = 255;
    public static final int PASSWORD_LENGTH = 6;
    public static final int PROFILE_IMG_SIZE = 36;
    public static final PasswordEncoder BCRYPT = new BCryptPasswordEncoder(12);

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

    public boolean checkImage(MultipartFile image) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        long fileLenth = image.getSize();
        return (extension.equals("jpg") || extension.equals("png")) && fileLenth < MAX_IMAGE_LENTH;
    }

    public RegResponse getImageError(MultipartFile photo) {
        RegResponse response = new RegResponse();
        String extension = FilenameUtils.getExtension(photo.getOriginalFilename());
        long fileLenth = photo.getSize();
        Map<String, String> errors = new HashMap<>();
        if (!extension.equals("jpg") && !extension.equals("png")) {
            errors.put("type", "Отправлен файл не формата изображение jpg, png");
        }
        if (fileLenth > MAX_IMAGE_LENTH) {
            errors.put("size", "Размер файла превышает допустимый размер");
        }
        response.setResult(false);
        response.setErrors(errors);
        return response;
    }

    public String saveImage(MultipartFile photo) throws IOException {
        RegResponse response = new RegResponse();
        String dir1 = gen(2);
        String dir2 = gen(2);
        String dir3 = gen(2);
        String newFileName = gen(5);
        String extension = FilenameUtils.getExtension(photo.getOriginalFilename());

        String dirName = "upload/" + dir1 + "/" + dir2 + "/" + dir3;
        newFileName = dirName + "/" + newFileName + "." + extension;
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BufferedImage bufferedImage = ImageIO.read(photo.getInputStream());
        File outputfile = new File(newFileName);
        ImageIO.write(bufferedImage, extension, outputfile);
        response.setString(newFileName);

        return "/" + newFileName;
    }

    public RegResponse comment(SetCommentRequest request,
                               Principal principal) {
        RegResponse regResponse = new RegResponse();
        Map<String, String> errors = new HashMap<>();
        Optional<Posts> postOpt = postsRepository.findById(request.getPostId());
        String text = request.getText();
        if (text.length() < MIN_COMMENT_LENTH) {
            errors.put("text", "Текст комментария отсутствует или менее " + MIN_COMMENT_LENTH + " символов");
        }
        if (errors.isEmpty()) {
            regResponse.setResult(true);
            PostComments pc = new PostComments();
            if (request.getParentId() > 0) {
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

    public boolean statisticIsPublic() {
        return globalSettingsRepository.findSettingValue("STATISTICS_IS_PUBLIC").equals("YES");
    }

    public boolean checkIfModerator(Principal principal) {
        return (userRepository.findIfModerator(principal.getName()) == 1);
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

    public RegResponse editImage(Principal principal, MultipartFile photo, String name, String email, String password, int removePhoto) throws IOException {
        User user = userRepository.findByEmail(principal.getName()).get();
        RegResponse response = new RegResponse();
        String extension = FilenameUtils.getExtension(photo.getOriginalFilename());
        long fileLenth = photo.getSize();
        if (photo != null) {
            Map<String, String> errors = new HashMap<>();
            List<String> emails = userRepository.findAll().stream()
                    .map(User::getEmail).collect(Collectors.toList());
            //          String email = request.getEmail();
            if (emails.contains(email)) {
                errors.put("email", "Этот e-mail уже зарегистрирован");
            }
            //          String name = request.getName();
            if (name.length() > MAX_LENGTH || !name.matches("[А-Яа-яA-Za-z]+([А-Яа-яA-Za-z\\s]+)?")) {
                errors.put("name", "Имя указано неверно");
            }
            if (!extension.equals("jpg") && !extension.equals("png")) {
                errors.put("type", "Отправлен файл не формата изображение jpg, png");
            }
            if (fileLenth > MAX_IMAGE_LENTH) {
                errors.put("size", "Размер файла превышает допустимый размер");
            }
            if (errors.isEmpty()) {
                response.setResult(true);
                BufferedImage bufferedImage = ImageIO.read(photo.getInputStream());
                BufferedImage resultImage = Scalr.resize(bufferedImage,
                        Scalr.Method.QUALITY,
                        PROFILE_IMG_SIZE,
                        PROFILE_IMG_SIZE);
                String toFile = "upload/" + user.getId() + "/" + photo.getOriginalFilename();

                Path path = Paths.get(toFile);
                if (!path.toFile().exists()) {

                    Files.createDirectories(path.getParent());
                    Files.createFile(path);

                    ImageIO.write(resultImage, extension, path.toFile());
                    File outputfile = new File(toFile);
                    ImageIO.write(bufferedImage, extension, outputfile);
                }
                user.setPhoto("/" + toFile.substring(toFile.lastIndexOf("upload")));
                userRepository.save(user);

            } else {
                response.setResult(false);
                response.setErrors(errors);
            }
        }
        return response;
    }

    public RegResponse profile(ProfileRequest request, Principal principal) throws IOException {
        Optional<User> userOpt = userRepository.findByEmail(principal.getName());
        RegResponse response = new RegResponse();
        if (userOpt.isPresent()) {
            Map<String, String> errors = new HashMap<>();
            List<String> emails = userRepository.findAll().stream()
                    .map(User::getEmail).collect(Collectors.toList());
            String email = request.getEmail();
            if (emails.contains(email)) {
                errors.put("email", "Этот e-mail уже зарегистрирован");
            }
            String name = request.getName();
            if (name.length() > MAX_LENGTH || !name.matches("[А-Яа-яA-Za-z]+([А-Яа-яA-Za-z\\s]+)?")) {
                errors.put("name", "Имя указано неверно");
            }
            String password = (request.getPassword() == null) ? "" : request.getPassword();
            if (password.length() < PASSWORD_LENGTH && password.length() != 0) {
                errors.put("password", "Пароль короче 6-ти символов");
            }

            if (errors.isEmpty()) {
                response.setResult(true);
                User user = userOpt.get();
                if (request.getName() != null) {
                    user.setName(name);
                }
                if (request.getEmail() != null) {
                    user.setEmail(email);
                }
                if (request.getPassword() != null) {
                    user.setPassword(BCRYPT.encode(request.getPassword()));
                }
                if (request.getRemovePhoto() == 1) {
                    user.setPhoto(null);
                    String toDelete = "upload/" + user.getId() + "/";
                    //        File errase = new File(toDelete);
                    FileUtils.deleteDirectory(new File(toDelete));
                }
                userRepository.save(user);
            } else {
                response.setResult(false);
                response.setErrors(errors);
            }
        } else {
            response.setResult(false);
        }
        return response;
    }

    public ResultResponse checkComment(SetCommentRequest request) {
        ResultResponse response = new ResultResponse();
        response.setResult(true);
        Optional<Posts> postOpt = postsRepository.findById(request.getPostId());
        Optional<PostComments> postCommOpt = postCommentsRepository.findById(request.getParentId());
        if (!postOpt.isPresent()) {
            response.setResult(false);
        }
        if (!postCommOpt.isPresent() && (request.getParentId() != 0)) {
            response.setResult(false);
        }
        return response;
    }

    public RegResponse getErrorResponse(SetCommentRequest request) {
        Map<String, String> errors = new HashMap<>();
        RegResponse response = new RegResponse();
        Optional<Posts> postOpt = postsRepository.findById(request.getPostId());
        Optional<PostComments> postCommOpt = postCommentsRepository.findById(request.getParentId());
        if (!postOpt.isPresent()) {
            errors.put("post", "Пост отсутствует");
        }
        if (!postCommOpt.isPresent()) {
            errors.put("comment", "Комментарий отсутствует");
        }
        response.setResult(false);
        response.setErrors(errors);
        return response;
    }

    public StatisticsResponse statisticsMy(Principal principal) {
        int likes = 0;
        int dislikes = 0;
        int views = 0;
        long first = mapperService.getTimestampFromLocalDateTime(LocalDateTime.now());
        List<Posts> posts = postsRepository.findMyActivePosts(userRepository.findByEmail(principal.getName()).get().getId());
        List<PostVotes> postVotes = postVotesRepository.findAll();
        for (Posts post : posts) {
            for (PostVotes pv : postVotes) {
                if (post.getId() == pv.getPost().getId() && pv.getValue() == 1) {
                    likes++;
                }
                if (post.getId() == pv.getPost().getId() && pv.getValue() == -1) {
                    dislikes++;
                }
            }
            views += post.getViewCount();
            first = Math.min(first, mapperService.getTimestampFromLocalDateTime(post.getTimestamp()));
        }
        StatisticsResponse response = new StatisticsResponse();
        response.setPostsCount(posts.size());
        response.setLikesCount(likes);
        response.setDislikesCount(dislikes);
        response.setViewsCount(views);
        response.setFirstPublication(response.getPostsCount() == 0 ? 0 : first);

        return response;
    }

    public StatisticsResponse statisticsAll() {
        int likes = 0;
        int dislikes = 0;
        int views = 0;
        long first = mapperService.getTimestampFromLocalDateTime(LocalDateTime.now());
        List<Posts> posts = postsRepository.findAll();
        List<PostVotes> postVotes = postVotesRepository.findAll();
        for (Posts post : posts) {
            for (PostVotes pv : postVotes) {
                if (post.getId() == pv.getPost().getId() && pv.getValue() == 1) {
                    likes++;
                }
                if (post.getId() == pv.getPost().getId() && pv.getValue() == -1) {
                    dislikes++;
                }
            }
            views += post.getViewCount();
            first = Math.min(first, mapperService.getTimestampFromLocalDateTime(post.getTimestamp()));
        }
        StatisticsResponse response = new StatisticsResponse();
        response.setPostsCount(posts.stream().count());
        response.setLikesCount(likes);
        response.setDislikesCount(dislikes);
        response.setViewsCount(views);
        response.setFirstPublication(first);

        return response;
    }

    public void settings(SettingsResponse request) {

        List<GlobalSettings> settings = globalSettingsRepository.findAll();
        for (GlobalSettings setting : settings) {
            switch (setting.getCode()) {
                case "MULTIUSER_MODE":
                    setting.setValue(request.isMultiuserMode() ? "YES" : "NO");
                    break;
                case "POST_PREMODERATION":
                    setting.setValue(request.isPostPremoderation() ? "YES" : "NO");
                    break;
                case "STATISTICS_IS_PUBLIC":
                    setting.setValue(request.isStatisticsIsPublic() ? "YES" : "NO");
                    break;
            }
            globalSettingsRepository.save(setting);
        }
    }
}
