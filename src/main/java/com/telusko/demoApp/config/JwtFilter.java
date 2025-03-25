package com.telusko.demoApp.config;

import com.telusko.demoApp.service.JWTService;
import com.telusko.demoApp.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtService.extractUserName(token);
        }
                                //Check if User is not already Authenticated
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //ApplicationCOntet ki madad se ek class ka object le krr aa rhe hai
            //but initially ye ek empty object hoga
//            UserDetails userDetails = context.getBean(UserDetails.class);
            //Instead we use
            //Getting data from the database
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);

            if(jwtService.validateToken(token , userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                //Authtoken ke saath request mein baaki details hai usko attach krr rhe hai
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Set that user is already Authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //Ab ye waala filter work ho chuka hai agle filter prr jaao
        filterChain.doFilter(request, response);
    }
}
