package main.api.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RegResponse {
    private boolean result;
    private Map<String,String>errors;
}
