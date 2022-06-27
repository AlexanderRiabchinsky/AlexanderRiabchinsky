package main.api.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CalendarResponse {
    private List<Integer> years;
    private ArrayList<PostCalendarResponse> posts;
}
