package main.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StatisticsResponse {
    long postsCount;
    int likesCount;
    int dislikesCount;
    int viewsCount;
    long firstPublication;
}
