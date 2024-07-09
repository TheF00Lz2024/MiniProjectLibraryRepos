package com.example.mongodb.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.SecretKey;
import java.io.IOException;

import static com.example.mongodb.model.Secret.SECRETKEY;

public class JwtUserFilter extends GenericFilterBean {

    Logger loggerError = LoggerFactory.getLogger(JwtUserFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("Authorization");
        try{
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
            } else {
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new ServletException("An exception had occur!");
                }
                final String token = authHeader.substring(7);
                SecretKey newKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRETKEY.toString()));
                Claims claim = Jwts.parser().verifyWith(newKey).build().parseSignedClaims(token).getPayload();
                String loginStatus = claim.getSubject().split(",")[1].trim();
                if (!loginStatus.trim().equalsIgnoreCase("Login: Success")) {
                    throw new ServletException("Unknown exception!");
                }
                request.setAttribute("claim", claim);
                request.setAttribute("user", servletRequest.getAttribute("username"));
                filterChain.doFilter(request, response);
            }
        } catch (Exception exception){
            loggerError.error(exception.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set appropriate status code
            response.getWriter().write(exception.getMessage());
        }
    }
}
