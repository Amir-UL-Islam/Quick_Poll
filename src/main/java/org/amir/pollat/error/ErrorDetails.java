package org.amir.pollat.error;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@ToString
public class ErrorDetails {
    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String path;
    private String developerMessage;

    private Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
}
