package com.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private void setAuthenticatedState(String username, HttpServletRequest request) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Boolean isAuthenticated = false;

        if (session != null && session.getAttribute("isAuthenticated") != null)
            isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");

        if (!isAuthenticated) {
            String token = getTokenFromRequest(request);

            if (StringUtils.hasText(token)) {
                try {
                    if (jwtTokenProvider.validateToken(token)) {
                        String username = jwtTokenProvider.getUsernameFromJWT(token);
                        String userToken = customUserDetailsService.getTokenByUsername(username);

                        if (token.equals(userToken)) {
                            // User is authenticated and token is valid
                            setAuthenticatedState(username, request);
                            session = request.getSession(true);
                            session.setAttribute("isAuthenticated", true);
                            session.setAttribute("username", username);
                        } else {
                            SecurityContextHolder.getContext().setAuthentication(null);

                            if (session != null && session.getAttribute("username") != null) {
                                customUserDetailsService.updateToken((String) session.getAttribute("username"), null);
                                session.invalidate();
                                response.setHeader("Authorization", "");
                            }
                        }
                    }
                } catch (AuthenticationCredentialsNotFoundException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": false, \"message\": \"Token was expired or incorrect\"}");
                    return;
                }
            }
        } else {
            String username = (String) session.getAttribute("username");
            setAuthenticatedState(username, request);
        }

        filterChain.doFilter(request, response);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}