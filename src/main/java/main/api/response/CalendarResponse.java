package main.api.response;

import lombok.Data;

@Data
public class CalendarResponse {
    private int years;
    private PostCalendarResponse posts;
}
