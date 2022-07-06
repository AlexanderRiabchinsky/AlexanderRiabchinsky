package main.api.response;

import lombok.Data;

import java.util.Map;

@Data
public class RegResponse {
    private boolean result;
    private Map<String,String>errors;
}
