package main.api.response;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

public class PostCalendarResponse {
    @Id
    private Date time;
    private int viewCount;
}
