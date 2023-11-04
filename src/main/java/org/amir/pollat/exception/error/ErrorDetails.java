package org.amir.pollat.exception.error;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
public class ErrorDetails {
    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String path;
    private String developerMessage;

    private Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
}
