package git.francrt.infrastructure.exception;

import git.francrt.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import jakarta.validation.ConstraintViolationException;
import git.francrt.domain.exception.PricesNotFoundException;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(Constants.VALIDATION_ERROR, ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(Constants.ERROR, Constants.BAD_REQUEST);
        body.put(Constants.MESSAGE, ex.getMessage());
        body.put(Constants.PATH, Constants.PATH_PRICES);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        log.error(Constants.VALIDATION_ERROR, ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(Constants.ERROR, Constants.BAD_REQUEST);
        body.put(Constants.MESSAGE, ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
        body.put(Constants.PATH, Constants.PATH_PRICES);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PricesNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePricesNotFoundException(PricesNotFoundException ex) {
        log.error(Constants.VALIDATION_ERROR, ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.NOT_FOUND.value());
        body.put(Constants.ERROR, Constants.NOT_FOUND);
        body.put(Constants.MESSAGE, ex.getMessage());
        body.put(Constants.PATH, Constants.PATH_PRICES);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error(Constants.VALIDATION_ERROR, ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(Constants.ERROR, Constants.BAD_REQUEST);
        body.put(Constants.MESSAGE, "Invalid parameter value for: " + ex.getName() + ". Expected type: " + (ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown"));
        body.put(Constants.PATH, Constants.PATH_PRICES);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error(Constants.VALIDATION_ERROR, ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(Constants.ERROR, Constants.BAD_REQUEST);
        body.put(Constants.MESSAGE, Constants.MSG_MALFORMED_JSON);
        body.put(Constants.PATH, Constants.PATH_PRICES);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        log.error(Constants.VALIDATION_ERROR, ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(Constants.ERROR, Constants.BAD_REQUEST);
        body.put(Constants.MESSAGE, "Missing required parameter: " + ex.getParameterName());
        body.put(Constants.PATH, Constants.PATH_PRICES);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        log.error(Constants.VALIDATION_ERROR, ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(Constants.ERROR, Constants.BAD_REQUEST);
        body.put(Constants.MESSAGE, ex.getMessage());
        body.put(Constants.PATH, Constants.PATH_PRICES);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.error(Constants.VALIDATION_ERROR, ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.METHOD_NOT_ALLOWED.value());
        body.put(Constants.ERROR, Constants.METHOD_NOT_ALLOWED);
        body.put(Constants.MESSAGE, Constants.MSG_METHOD_NOT_ALLOWED);
        body.put(Constants.PATH, Constants.PATH_PRICES);
        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error(Constants.INTERNAL_ERROR, ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put(Constants.ERROR, Constants.INTERNAL_SERVER_ERROR);
        body.put(Constants.MESSAGE, Constants.MSG_INTERNAL_ERROR);
        body.put(Constants.PATH, Constants.PATH_PRICES);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
