package main.service;

import lombok.AllArgsConstructor;
import main.api.response.*;
import main.model.Posts;
import main.model.Tags;
import main.repositories.GlobalSettingsRepository;
import main.repositories.PostsRepository;
import main.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
            tagDT.setWeight((double) Math.round(tagDT.getWeight() / weightMax * 1000) / 1000);
        }
        tagResponse.setTags(tagsDT);
        return tagResponse;
    }

    public CalendarResponse getCalendar(String year) {
        CalendarResponse calendarResponse = new CalendarResponse();
        int requestYear = year==null ? LocalDateTime.now().getYear() : Integer.parseInt(year);
        String[] allYearsPost = postsRepository.findAllYearValue();
        calendarResponse.setYears(allYearsPost);
        //DateTimeFormatter formatDate = DateTimeFormatter.ofPattern(config.getTimeDateFormat());
        Map<String, Integer> dateCount = new HashMap<>();

        List<String> datesByYear = postsRepository.findPostDatesByYear(String.valueOf(requestYear));
        for (String data:datesByYear){
            dateCount.put(data,postsRepository.findPostNumberByDate(data));
        }
        calendarResponse.setPosts(dateCount);

        return calendarResponse;
    }

    public boolean isMultiUser() {
        return globalSettingsRepository.findSettingValue("MULTIUSER_MODE").equals("YES");
    }
    public long getTimestampFromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? 0 : localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

}
