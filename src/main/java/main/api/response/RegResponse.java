package main.api.response;

import lombok.Data;

import java.util.Map;

@Data
public class RegResponse {
    boolean result;
    Map<String,String>errors;
}
