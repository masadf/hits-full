package itmo.serverhitsapp.exceptions;

import io.jsonwebtoken.JwtException;
import itmo.serverhitsapp.interaction.ApiError;
import jakarta.servlet.ServletException;
import org.hibernate.HibernateException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({AuthException.class, JwtException.class})
    public ResponseEntity<ApiError> authExceptionHandler(Exception exception) {
        return new ResponseEntity<>(ApiError.builder()
                .statusNum(UNAUTHORIZED.value())
                .message(exception.getMessage())
                .build(), UNAUTHORIZED);
    }

    @ExceptionHandler({HttpMessageConversionException.class, ServletException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiError> incorrectRequestExceptionHandler(Exception exception) {
        return new ResponseEntity<>(ApiError.builder()
                .statusNum(FORBIDDEN.value())
                .message("Некорректный запрос!")
                .build(), FORBIDDEN);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiError> incorrectRequestMethodExceptionHandler(HttpRequestMethodNotSupportedException exception) {
        return new ResponseEntity<>(FORBIDDEN);
    }

    @ExceptionHandler({InvalidParameterException.class})
    public ResponseEntity<ApiError> invalidParameterExceptionHandler(InvalidParameterException exception) {
        return new ResponseEntity<>(ApiError.builder()
                .statusNum(FORBIDDEN.value())
                .message(exception.getMessage())
                .build(), FORBIDDEN);
    }

    @ExceptionHandler({HibernateException.class})
    public ResponseEntity<ApiError> databaseExceptionHandler(HibernateException exception) {
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }
}
