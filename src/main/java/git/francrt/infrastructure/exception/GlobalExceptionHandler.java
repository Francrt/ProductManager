package git.francrt.infrastructure.exception;

import git.francrt.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import git.francrt.domain.exception.PricesNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(Constants.ERROR_VALIDACION, ex.getMessage());
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
        log.error(Constants.ERROR_VALIDACION, ex.getMessage());
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
        log.error(Constants.ERROR_VALIDACION, ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.NOT_FOUND.value());
        body.put(Constants.ERROR, Constants.NOT_FOUND);
        body.put(Constants.MESSAGE, ex.getMessage());
        body.put(Constants.PATH, Constants.PATH_PRICES);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
