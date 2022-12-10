package itmo.serverhitsapp.exceptions;

import io.jsonwebtoken.JwtException;
import itmo.serverhitsapp.interaction.ApiError;
import org.hibernate.HibernateException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({InvalidParameterException.class})
    public ResponseEntity<ApiError> invalidParameterExceptionHandler(InvalidParameterException exception) {
        return new ResponseEntity<>(ApiError.builder()
                .statusNum(FORBIDDEN.value())
                .message(exception.getMessage())
                .build(), FORBIDDEN);
    }

    @ExceptionHandler({AuthException.class, JwtException.class, AuthenticationException.class})
    public ResponseEntity<ApiError> authExceptionHandler(Exception exception) {
        return new ResponseEntity<>(ApiError.builder()
                .statusNum(UNAUTHORIZED.value())
                .message(exception.getMessage())
                .build(), UNAUTHORIZED);
    }

    @ExceptionHandler({HibernateException.class})
    public ResponseEntity<ApiError> databaseExceptionHandler(HibernateException exception) {
        return new ResponseEntity<>(ApiError.builder()
                .statusNum(FORBIDDEN.value())
                .message("Сервис в данный момент работает некорректно! Обратитесь позже")
                .build(), FORBIDDEN);
    }

}
