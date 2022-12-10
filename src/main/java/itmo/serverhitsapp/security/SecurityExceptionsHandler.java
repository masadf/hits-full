package itmo.serverhitsapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.serverhitsapp.interaction.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class SecurityExceptionsHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiError apiError = ApiError.builder()
                .statusNum(401)
                .message("Пользователь не авторизован!")
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        OutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream, apiError);

        outputStream.flush();
    }
}
