package main.api.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
@Getter
@Setter
public class PostCalendarResponse {
    @Id
    private Date time;
    private int viewCount;
}
