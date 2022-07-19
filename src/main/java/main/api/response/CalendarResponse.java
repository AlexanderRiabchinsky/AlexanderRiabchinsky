package main.api.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CalendarResponse {
    private String[] years;
    private Map<String, Integer> posts;
}
