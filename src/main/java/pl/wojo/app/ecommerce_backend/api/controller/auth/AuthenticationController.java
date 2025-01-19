package pl.wojo.app.ecommerce_backend.api.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pl.wojo.app.ecommerce_backend.api.exception.EmailAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.api.exception.UsernameAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.api.model.LoginBody;
import pl.wojo.app.ecommerce_backend.api.model.LoginResponse;
import pl.wojo.app.ecommerce_backend.api.model.RegistrationBody;
import pl.wojo.app.ecommerce_backend.api.model.SavedUser;
import pl.wojo.app.ecommerce_backend.api.service.UserService;

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
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = userService.loginUser(loginBody);

        if(jwt == null) {   // wrong 'username' or 'password'
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);   // empty body
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(jwt);

        return ResponseEntity.ok(loginResponse); // utworzyliśmy klasę przechowującą JWT, dzięki niej w przyszłości jeśli będziemy chcieli wysyłać dodatkowe informacje w odpowiedni to dodamy je w polach klasy LoginResponse
    }
}
