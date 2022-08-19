package main.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RegResponse {
    private boolean result;
    int id;
    private Map<String, String> errors;
    String string;
}
