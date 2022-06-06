package main.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
@Entity
public class PostCalendarResponse {
    @Id
    private Date time;
    private int viewCount;
}
