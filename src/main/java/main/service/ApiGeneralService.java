package main.service;

import lombok.AllArgsConstructor;
import main.api.response.*;
import main.model.Tags;
import main.repositories.GlobalSettingsRepository;
import main.repositories.PostsRepository;
import main.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

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
        ArrayList<TagExternal> tags1 = new ArrayList<>();
        //Пробуем собрать тэги содержащие query
        int weightMax = 0;
        if (!(query == null)) {
            for (Tags tag : tags) {
                TagExternal tagExt = new TagExternal();
                tagExt.setName(tag.getName());
                int weight = tagsRepository.findPostNumber(tag.getId());
                if (weight > weightMax) {
                    weightMax = weight;
                }
                tagExt.setWeight(weight);
                if (tag.getName().contains(query)) {
                    tags1.add(tagExt);
                }
            }
        }
        //Если тэгов с query нет, берем все тэги
        if (tags1.size() == 0) {
            weightMax = 0;
            for (Tags tag : tags) {
                TagExternal tagExt = new TagExternal();
                tagExt.setName(tag.getName());
                int weight = tagsRepository.findPostNumber(tag.getId());
                if (weight > weightMax) {
                    weightMax = weight;
                }
                tagExt.setWeight(weight);
                tags1.add(tagExt);
            }
        }
        //Нормирование весов
        for (TagExternal tag1 : tags1) {
            tag1.setWeight((double) Math.round(tag1.getWeight() / weightMax * 1000) / 1000);
        }
        tagResponse.setTags(tags1);
        return tagResponse;
    }

    public CalendarResponse getCalendar(String year) {
        if(year==null){year= String.valueOf((new Date().getYear())+1900);
        }
        CalendarResponse calendarResponse = new CalendarResponse();
        List<PostCalendarResponse> item = new ArrayList<>();
        calendarResponse.setYear(year);
        List<String> datesByYear = postsRepository.findPostDatesByYear(year);
        for (String data:datesByYear){
            PostCalendarResponse view = new PostCalendarResponse();
            view.setDate(data);
            view.setViewCount(postsRepository.findPostNumberByDate(data));
            item.add(view);
        }
        calendarResponse.setPosts(item);

        return calendarResponse;
    }

    public boolean isMultiUser() {
        return globalSettingsRepository.findSettingValue("MULTIUSER_MODE").equals("YES");
    }

}
