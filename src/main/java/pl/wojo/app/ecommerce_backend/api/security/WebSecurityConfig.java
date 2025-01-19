package pl.wojo.app.ecommerce_backend.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityConfig(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable());
        
        http.authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll());
        
        http.cors(corsCustomizer -> corsCustomizer.disable());  // we are not requiring cors restrictions beacause we are using Postman which does not use browser
        
        return http.build();
    }
}