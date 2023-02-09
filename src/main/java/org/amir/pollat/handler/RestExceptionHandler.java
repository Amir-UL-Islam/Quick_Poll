package org.amir.pollat.handler;

import org.amir.pollat.error.ErrorDetails;
import org.amir.pollat.error.ValidationError;
import org.amir.pollat.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {
//    @ExceptionHandler(ResourceNotFoundException.class)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleResourceNotFoundException(MethodArgumentNotValidException ex, HttpServletRequest request, HttpServletResponse response) {

        // Populate errorDetails instance
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(System.currentTimeMillis());
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());

//        Mainly Using the servlet API, we can get the request URI and set it as the path in the ErrorDetails object.
        String requestPath = (String) request.getAttribute("javax.servlet.error.request_uri");
        if(requestPath == null) {
            requestPath = request.getRequestURI();
        }
        errorDetails.setPath(requestPath);

//        Create ValidationError instances
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for(FieldError fieldError: fieldErrors) {
            List<ValidationError> validationErrorList = errorDetails.getErrors().get(fieldError.getField());
            if(validationErrorList == null) {
                validationErrorList = new ArrayList<ValidationError>();
                errorDetails.getErrors().put(fieldError.getField(), validationErrorList);
            }

            ValidationError validationError = new ValidationError();
            validationError.setCode(fieldError.getCode());
            validationError.setMessage(fieldError.getDefaultMessage());
            validationErrorList.add(validationError);

        }


        errorDetails.setTitle("Resource not found");
        errorDetails.setDetail(ex.getMessage());
        errorDetails.setDeveloperMessage(ex.getClass().getName());
        return new ResponseEntity<>(errorDetails, null, HttpStatus.BAD_REQUEST);
    }
}
