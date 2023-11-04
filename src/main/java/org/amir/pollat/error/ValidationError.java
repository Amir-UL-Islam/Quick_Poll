package org.amir.pollat.error;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ValidationError {
    private String code;
    private String message;
}
