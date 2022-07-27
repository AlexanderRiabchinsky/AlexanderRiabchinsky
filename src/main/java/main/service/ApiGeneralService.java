package main.service;

import lombok.AllArgsConstructor;
import main.api.request.RegPostRequest;
import main.api.request.SetCommentRequest;
import main.api.response.*;
import main.model.Posts;
import main.model.Tags;
import main.repositories.GlobalSettingsRepository;
import main.repositories.PostsRepository;
import main.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        int requestYear = year==null ? LocalDateTime.now().getYear() : Integer.parseInt(year);
        String[] allYearsPost = postsRepository.findAllYearValue();
        calendarResponse.setYears(allYearsPost);
        Map<String, Integer> dateCount = new HashMap<>();

        List<String> datesByYear = postsRepository.findPostDatesByYear(String.valueOf(requestYear));
        for (String data:datesByYear){
            dateCount.put(data,postsRepository.findPostNumberByDate(data));
        }
        calendarResponse.setPosts(dateCount);

        return calendarResponse;
    }

    public RegResponse saveImage(MultipartFile photo){
        RegResponse response = new RegResponse();
        String dir1 = gen(2);
        String dir2 = gen(2);
        String dir3 = gen(2);
        String fileName = gen(5);

        return response;
    }

    public RegResponse comment(SetCommentRequest setCommentRequest,
                               Principal principal) {
        RegResponse regResponse = new RegResponse();
//        Map<String, String> errors = new HashMap<>();
//        String title = regRequest.getTitle();
//        if(title.length()<TITLE_LENGTH){
//            errors.put("title","Название поста короче 3 символов");
//        }
//        String text = regRequest.getText();
//        if(text.length()<TEXT_LENGTH){
//            errors.put("text","Текст поста короче 50 символов");
//        }
//        if (errors.isEmpty()) {
//            regResponse.setResult(true);
//            Posts post = new Posts();
//            post.setId(id);
//            post.setIsActive(regRequest.getActive());
//            ModerationStatus status = ((!apiGeneralService.isPostPremoderated()|getAuthorizedUser(principal).getIsModerator()==1)&regRequest.getActive()==1) ? ModerationStatus.ACCEPTED : ModerationStatus.NEW;
//            post.setStatus(status);
//            post.setModeratorId((getAuthorizedUser(principal).getIsModerator()==1)? getAuthorizedUser(principal).getId(): null);
//            post.setUser(getAuthorizedUser(principal));
//
//            LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(regRequest.getTimestamp(), 0, ZoneOffset.UTC);
//            int result = localDateTime.compareTo(LocalDateTime.now());
//            LocalDateTime time = (result<0)? LocalDateTime.now():localDateTime;
//            post.setTimestamp(time);
//
//            post.setTitle(title);
//            post.setText(text);
//            post.setViewCount(0);
//            postsRepository.save(post);
//            for(Tags tag:regRequest.getTags()){tagsRepository.save(tag);}
//        } else {
//            regResponse.setResult(false);
//            regResponse.setErrors(errors);
//        }
        return regResponse;
    }

    public boolean isMultiUser() {
        return globalSettingsRepository.findSettingValue("MULTIUSER_MODE").equals("YES");
    }
    public boolean isPostPremoderated() {
        return globalSettingsRepository.findSettingValue("POST_PREMODERATION").equals("YES");
    }
        public static String gen(int length) {
            StringBuffer sb = new StringBuffer();
            for (int i = length; i > 0; i -= 12) {
                int n =  min(12, abs(i));
                sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
            }
            return sb.toString();
    }
}
