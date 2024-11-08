package com.systemdesign.tutorials.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.systemdesign.tutorials.tokens.JwtAuthenticationToken;
import com.systemdesign.tutorials.util.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response,
                                    jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                // Validate token and decode claims using java-jwt
                DecodedJWT decodedJWT = jwtUtil.validateToken(token);
                String username = decodedJWT.getSubject();

                if (username != null) {
                    // Set the SecurityContext with authenticated user
                    SecurityContextHolder.getContext().setAuthentication(
                            new JwtAuthenticationToken(username)
                    );
                }
            } catch (JWTVerificationException e) {
                // Log the invalid token exception, if necessary
                System.out.println("Invalid JWT Token: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
