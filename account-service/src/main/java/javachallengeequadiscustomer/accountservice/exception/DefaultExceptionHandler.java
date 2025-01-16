package javachallengeequadiscustomer.accountservice.exception;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.List;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handle(ResourceNotFoundException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(),
                                         e.getMessage(),
                                         HttpStatus.NOT_FOUND.value(),
                                         ZonedDateTime.now(),
                                         List.of());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handle(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<String> errors = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ApiError apiError = new ApiError(request.getRequestURI(),
                                         "At least one argument for this method is invalid",
                                         HttpStatus.BAD_REQUEST.value(),
                                         ZonedDateTime.now(),
                                         errors);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiError> handle(FeignException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(),
                                         "Failed to connect to external service. Please try again in a few minutes",
                                         HttpStatus.BAD_GATEWAY.value(),
                                         ZonedDateTime.now(),
                                         List.of());

        return new ResponseEntity<>(apiError, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handle(DuplicateResourceException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(),
                                         e.getMessage(),
                                         HttpStatus.CONFLICT.value(),
                                         ZonedDateTime.now(),
                                         List.of());

        return new ResponseEntity<>(apiError, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handle(Exception e, HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(),
                                         e.getMessage(),
                                         HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                         ZonedDateTime.now(),
                                         List.of());

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
