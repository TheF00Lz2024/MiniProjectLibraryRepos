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
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.SecretKey;
import java.io.IOException;

public class JwtRolesFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String authHeader = request.getHeader("Authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ServletException("An exception had occur!");
            }
            final String token = authHeader.substring(7);
            SecretKey newKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"));
            Claims claim = Jwts.parser().verifyWith(newKey).build().parseSignedClaims(token).getPayload();
            String[] stringData = claim.getSubject().split(",");
            String loginStatus = stringData[2].trim();
            String userRoles = stringData[1].trim();
            if (!loginStatus.equalsIgnoreCase("Login: Success")) {
                throw new ServletException("Unknown exception");
            }
            if (!userRoles.equalsIgnoreCase("Roles: Author")) {
                throw new ServletException("Invalid Role");
            }
            request.setAttribute("claim", claim);
            request.setAttribute("user", servletRequest.getAttribute("username"));
            filterChain.doFilter(request, response);
        }
    }
}
