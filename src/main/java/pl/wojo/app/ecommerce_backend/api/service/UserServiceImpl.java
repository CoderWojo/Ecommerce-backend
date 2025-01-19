package pl.wojo.app.ecommerce_backend.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pl.wojo.app.ecommerce_backend.api.exception.EmailAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.api.exception.UsernameAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.api.model.LoginBody;
import pl.wojo.app.ecommerce_backend.api.model.RegistrationBody;
import pl.wojo.app.ecommerce_backend.api.model.SavedUser;
import pl.wojo.app.ecommerce_backend.api.repository.LocalUserRepository;
import pl.wojo.app.ecommerce_backend.model.LocalUser;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final LocalUserRepository repository;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;

    public UserServiceImpl(LocalUserRepository repository, EncryptionService encryptionService, JWTService jwtService) {//JpaRepository
        this.repository = repository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    // Jeśli metoda jest wywoływana z innego serwisu to bez oznaczenia @Valid, parametr nie zostanie zwalidowany 
    @Override
    public SavedUser registerUser(RegistrationBody registrationBody) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
    
        String username = registrationBody.getUsername();
        String email = registrationBody.getEmail();
        // wyrzuć wyjątek, gdy istnieje juz
        if(repository.findByUsername(username).isPresent()) {
            log.warn("Attempt to register with an existing email: {}", email);
            throw new UsernameAlreadyExistsException("Username: " + username + " already exists! Please use another.");
        }

        if(repository.findByEmailIgnoreCase(email).isPresent()) {
            log.warn("Attempt to register with an existing username: {}", username);
            throw new EmailAlreadyExistsException("Email: " + email + " already exists! Please use another.");
        }
        
        LocalUser localUser = convertRegistrationBodyToLocalUser(registrationBody);
        // password encrypting
        localUser.setPassword(encryptionService.encryptPassword(localUser.getPassword()));
        LocalUser savedUser = repository.save(localUser);
        
        /*  Możesz dodać więcej logiki po zapisie
            notificationService.sendWelcomeEmail(savedUser);
            loggingService.logUserCreation(savedUser);
        */
        
        log.info("Successfully registered user with username: {}", username);

        return convertLocalUserToSavedUser(savedUser);
    }

    @Override
    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> opUser = repository.findByUsername(loginBody.getUsername());

        if(opUser.isPresent()) {
            LocalUser user = opUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                // hasła się zgadzają, zwracamy JWT
                return jwtService.generateJWT(user);    // utwórz JWT na podstawie danych zawartych w bazie
            }
        }
        return null;
    }
    
    

    private static LocalUser convertRegistrationBodyToLocalUser(RegistrationBody rb) {
        return LocalUser.builder()
            .username(rb.getUsername())
            .password(rb.getPassword())
            .email(rb.getEmail())
            .firstName(rb.getFirstName())
            .lastName(rb.getLastName())
            .build();   // without addresses    
    }
    
    private static SavedUser convertLocalUserToSavedUser(LocalUser localUser) {
        return SavedUser.builder()  
            .username(localUser.getUsername())
            .email(localUser.getEmail())
            .firstName(localUser.getFirstName())
            .lastName(localUser.getLastName())
            .build();
    }
}
