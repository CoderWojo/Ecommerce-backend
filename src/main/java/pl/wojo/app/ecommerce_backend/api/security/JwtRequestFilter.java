package pl.wojo.app.ecommerce_backend.api.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTDecodeException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.wojo.app.ecommerce_backend.api.repository.LocalUserRepository;
import pl.wojo.app.ecommerce_backend.api.service.JWTService;
import pl.wojo.app.ecommerce_backend.model.LocalUser;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private LocalUserRepository repository;

    public JwtRequestFilter(JWTService jwtService, LocalUserRepository repository) {
        this.jwtService = jwtService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
        throws ServletException, IOException {
            // extracting jwt from a header
            String tokenHeader = request.getHeader("Authorization");
            
            if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {  // żądanie z nagłówkiem Authorization, czyli kolejne po wcześniej udanym logowaniu
                // checking
                String token = tokenHeader.substring(7);
                
                try {
                    String username = jwtService.getUsername(token);
                    Optional<LocalUser> opUser = repository.findByUsername(username);
                    
                    if(opUser.isPresent()) {
                        LocalUser user = opUser.get();

                        // we're creating authentication representation object 
                        // credentials=null, as 
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // ip and sessionId to inform Spring Security and Spring MVC

                        SecurityContext context = SecurityContextHolder.getContext();
                        context.setAuthentication(authentication);
                    }
                } catch(JWTDecodeException e) { // nie poprawne dane jwt
                    
                }


            }   
            // token could not be created 

            filterChain.doFilter(request, response);
    }
    
}
