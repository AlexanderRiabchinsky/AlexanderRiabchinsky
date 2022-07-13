package main.service;

import main.api.response.CalendarResponse;
import main.api.response.SettingsResponse;
import main.api.response.TagExternal;
import main.api.response.TagResponse;
import main.model.Tags;
import main.repositories.PostsRepository;
import main.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ApiGeneralService{
    @Autowired
    private TagsRepository tagsRepository;
    private PostsRepository postsRepository;
    public SettingsResponse getGlobalSettings(){
        SettingsResponse settingsResponse=new SettingsResponse();
        settingsResponse.setMultiuserMode(true);
        settingsResponse.setStatisticsIsPublic(true);
        settingsResponse.setPostPremoderation(false);
        return settingsResponse;
    }
    public TagResponse getTagResponse(String query) {
 //       long totalPosts = postsRepository.count();
        Map<String,Integer> postweights = new TreeMap<>();
        List<Tags> tags = tagsRepository.findAll();

        TagResponse tagResponse = new TagResponse();
        ArrayList<TagExternal> tags1 = new ArrayList<>();
        //Пробуем собрать тэги содержащие query
        int weightMax = 0;
        for (Tags tag:tags) {if (tag.getName().contains(query)){
            TagExternal tagExt = new TagExternal();
            tagExt.setName(tag.getName());
            int weight = tagsRepository.findPostNumber(tag.getId());
            if (weight>weightMax){weightMax=weight;}
            tagExt.setWeight(weight);
            tags1.add(tagExt);
        }}

        //Если тэгов с query нет, берем все тэги
        if (tags1.size()==0){
            weightMax = 0;
            for (Tags tag:tags) {
                TagExternal tagExt = new TagExternal();
                tagExt.setName(tag.getName());
                int weight = tagsRepository.findPostNumber(tag.getId());
                if (weight>weightMax){weightMax=weight;}
                tagExt.setWeight(weight);
                tags1.add(tagExt);}
        }
        //Нормирование весов
        for (TagExternal tag1:tags1){tag1.setWeight(tag1.getWeight()/weightMax);}
        tagResponse.setTags(tags1);
        return tagResponse;
    }
    public CalendarResponse getCalendar(int year){
        CalendarResponse calendarResponse = new CalendarResponse();

        return calendarResponse;
    }

}
