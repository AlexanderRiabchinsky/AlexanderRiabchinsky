package main.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StatisticsResponse {
    int postsCount;
    int likesCount;
    int dislikesCount;
    int viewCount;
    long firstPublication;
}
