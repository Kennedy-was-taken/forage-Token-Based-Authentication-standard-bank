package com.JwtAuthentication.TaskOne.Jwt;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.JwtAuthentication.TaskOne.Service.JwtUserDetailService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

    private static Log logger = LogFactory.getLog(JwtRequestFilter.class);

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String tokenHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            try{
                token = tokenHeader.substring(7, tokenHeader.length());
                username = jwtTokenUtil.getUsernameFromToken(token);
            }
            catch(MalformedJwtException e){
                logger.error("Invalid token : " + e.getMessage());
            }
            catch(ExpiredJwtException e){
                logger.error("token is expired " + e.getMessage());
            }
            catch(UnsupportedJwtException e){
                logger.error("Jwt token is unsupported : " + e.getMessage());
            }
        }
        else{
            logger.error("theres no bearer in the tokenHeader");
        }

        //once the token is retrieved, validate it
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);

            /* 
             * if token is valid, configuring Spring Security to manually set
			 * authentication
             */
            if(jwtTokenUtil.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                /* 
                 * After setting the Authentication in the context, we specify
				 * that the current user is authenticated. So it passes the
				 * Spring Security Configurations successfully.
                 */
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } 
        }

        filterChain.doFilter(request, response);
    }
    
}
