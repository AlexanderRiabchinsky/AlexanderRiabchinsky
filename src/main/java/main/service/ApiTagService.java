package main.service;

import main.api.response.TagExternal;
import main.api.response.TagResponse;
import main.model.Tags;
import main.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiTagService {
    @Autowired
    private TagsRepository tagsRepository;

    public TagResponse getTagResponse() {
        TagResponse tagResponse = new TagResponse();
        List<Tags> tags = tagsRepository.findAll();
        ArrayList<TagExternal> tags1 = new ArrayList<>();
        for (Tags tag:tags) {
            TagExternal tagExt = new TagExternal();

            tagExt.setName("Lena");
            tagExt.setWeight(0.9);
            tags1.add(tagExt);}
        tagResponse.setTags(tags1);
        return tagResponse;
    }
}
