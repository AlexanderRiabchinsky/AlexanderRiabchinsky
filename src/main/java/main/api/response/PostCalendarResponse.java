package main.api.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
@Getter
@Setter
public class PostCalendarResponse {
    private String date;
    private int viewCount;
}
