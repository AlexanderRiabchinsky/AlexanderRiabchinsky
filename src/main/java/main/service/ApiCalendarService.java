package main.service;

import main.api.response.CalendarResponse;
import main.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiCalendarService {
    @Autowired
    private PostsRepository postsRepository;

    public CalendarResponse getCalendarResponse(){
        CalendarResponse calendarResponse = new CalendarResponse();
      //  calendarResponse.setYears(postsRepository.findAll().get());
        return calendarResponse;
    }
}
