package fr.nantes.poc.springmongo.exception;

import fr.nantes.poc.springmongo.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError("Erreur interne, Merci de contacter votre administrateur");
        error.setMessage("Exception: " + ex.getClass().getSimpleName() + ", error: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedExceptionHandler(AccessDeniedException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setError("Accès interdit");
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RestResponseException.class)
    public ResponseEntity<ErrorResponse> restResponseExceptionHandler(RestResponseException ex) {
        ErrorResponse error = new ErrorResponse();
        HttpStatus httpStatus = ex.getStatus() == null ? HttpStatus.INTERNAL_SERVER_ERROR : ex.getStatus();

        error.setStatus(httpStatus.value());
        error.setError(httpStatus.getReasonPhrase());
        error.setMessage(ex.getMessage());

        if (StringUtils.isEmpty(ex.getMessage())) {
            error.setMessage("Une erreur c'est produite");
        }

        if (ex.getResult() != null) {
            final Map<String, String> errorList = new HashMap<>();
            ex.getResult().getFieldErrors().forEach(e -> errorList.put(e.getField(), e.getDefaultMessage()));
            error.setErrorList(errorList);
        }

        return new ResponseEntity<>(error, httpStatus);
    }
}
