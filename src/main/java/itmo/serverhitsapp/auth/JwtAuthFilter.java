package itmo.serverhitsapp.auth;

import io.jsonwebtoken.JwtException;
import itmo.serverhitsapp.exceptions.IncorrectUserCredentialsException;
import itmo.serverhitsapp.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService usersDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            UserDetails userDetails = loadUserDetails(request);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JwtException | IncorrectUserCredentialsException ignored) {
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private UserDetails loadUserDetails(HttpServletRequest request) {
        String token = getAccessTokenFromAuthorizationHeader(request);
        String username = jwtUtils.validateAccessToken(token).getUsername();
        return usersDetailsService.loadUserByUsername(username);
    }

    private String getAccessTokenFromAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
