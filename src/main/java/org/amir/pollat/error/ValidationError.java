package org.amir.pollat.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationError {
    private String code;
    private String message;
}
