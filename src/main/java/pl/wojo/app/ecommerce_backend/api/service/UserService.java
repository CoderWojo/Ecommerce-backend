package pl.wojo.app.ecommerce_backend.api.service;

import pl.wojo.app.ecommerce_backend.api.exception.EmailAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.api.exception.UsernameAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.api.model.LoginBody;
import pl.wojo.app.ecommerce_backend.api.model.RegistrationBody;
import pl.wojo.app.ecommerce_backend.api.model.SavedUser;

public interface UserService {    
    
    SavedUser registerUser(RegistrationBody registrationBody) 
        throws UsernameAlreadyExistsException, EmailAlreadyExistsException;

    String loginUser(LoginBody loginBody);
}
