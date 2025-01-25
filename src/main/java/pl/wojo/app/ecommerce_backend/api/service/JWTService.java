package pl.wojo.app.ecommerce_backend.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.annotation.PostConstruct;
import pl.wojo.app.ecommerce_backend.model.LocalUser;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")  // pole nie może być statyczne bo spring wstrzykuje wartości z @Value tylko polom przypisanym do instancji a nie do klasy, ponadto fakt że jest to @Service wymaga aby pola były przypisane do instancji
    private String SECRET_KEY;// secret key

    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    @Value("${jwt.issuer}")
    private String ISSUER;

    private static String USERNAME = "USERNAME";

    private static String EMAIL = "EMAIL";

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(SECRET_KEY); 
    }

    public String generateJWT(LocalUser user) {
        return JWT.create()
            .withSubject(user.getId().toString())   // id
            .withClaim(USERNAME, user.getUsername())    // użytkownik
            .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds))) // data wygaśnięcia
            .withClaim(EMAIL, user.getEmail())
            .withIssuer(ISSUER) // Issuer 
            .withIssuedAt(new Date())   // data wygenerowania
            .sign(algorithm);   // podpisanie tokenu
    }

    // For checking wheter the User account is active/not banned
    public String getUsername(String JWTtoken) {
        return JWT.decode(JWTtoken).getClaim(USERNAME).asString();
    }
}
