package main.service;

import main.api.response.CalendarResponse;
import main.api.response.SettingsResponse;
import main.api.response.TagExternal;
import main.api.response.TagResponse;
import main.model.Tags;
import main.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiGeneralService {
    @Autowired
    private TagsRepository tagsRepository;
    public SettingsResponse getGlobalSettings(){
        SettingsResponse settingsResponse=new SettingsResponse();
        settingsResponse.setMultiuserMode(true);
        settingsResponse.setStatisticsIsPublic(true);
        settingsResponse.setPostPremoderation(false);
        return settingsResponse;
    }
    public TagResponse getTagResponse(String query) {
        TagResponse tagResponse = new TagResponse();
        List<Tags> tags = tagsRepository.findAll();
        ArrayList<TagExternal> tags1 = new ArrayList<>();
        for (Tags tag:tags) {if (tag.getName().contains(query)){
            TagExternal tagExt = new TagExternal();

            tagExt.setName(tag.getName());
            tagExt.setWeight(tag.getId());
            tags1.add(tagExt);}}

        if (tags1.size()==0){
            for (Tags tag:tags) {
                TagExternal tagExt = new TagExternal();

                tagExt.setName(tag.getName());
                tagExt.setWeight(1);
                tags1.add(tagExt);}
        }
        tagResponse.setTags(tags1);
        return tagResponse;
    }
    public CalendarResponse getCalendar(String year){
        CalendarResponse calendarResponse = new CalendarResponse();

        return calendarResponse;
    }
}
