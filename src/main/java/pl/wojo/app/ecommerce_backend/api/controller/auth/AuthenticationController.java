package pl.wojo.app.ecommerce_backend.api.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pl.wojo.app.ecommerce_backend.api.exception.EmailAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.api.exception.UsernameAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.api.model.LoginBody;
import pl.wojo.app.ecommerce_backend.api.model.RegistrationBody;
import pl.wojo.app.ecommerce_backend.api.model.SavedUser;
import pl.wojo.app.ecommerce_backend.api.service.UserService;
import pl.wojo.app.ecommerce_backend.model.LocalUser;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    // Kontroler zwraca dane do warstwy serwisowej
    @PostMapping("/register")
    public ResponseEntity<SavedUser> registerUser(@RequestBody @Valid RegistrationBody registrationBody) {
        try {
            SavedUser savedUser = userService.registerUser(registrationBody);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = userService.loginUser(loginBody);

        if(jwt == null) {   // wrong 'username' or 'password'
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);   // empty body
        }
        // LoginResponse loginResponse = new LoginResponse();
        // loginResponse.setJwt(jwt);


        return ResponseEntity.ok()
            .header("Authentication", "Bearer " + jwt)
            .build(); // utworzyliśmy klasę przechowującą JWT, dzięki niej w przyszłości jeśli będziemy chcieli wysyłać dodatkowe informacje w odpowiedni to dodamy je w polach klasy LoginResponse
    }

    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
        // pobiera 'principle' z securityContext
        return user;
    }
    
}
