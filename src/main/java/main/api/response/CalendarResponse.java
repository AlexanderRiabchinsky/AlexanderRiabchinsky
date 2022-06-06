package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.PostCalendarResponse;

import java.sql.Date;
import java.util.List;

@Data
public class CalendarResponse {
    private int years;
    private PostCalendarResponse posts;
}
