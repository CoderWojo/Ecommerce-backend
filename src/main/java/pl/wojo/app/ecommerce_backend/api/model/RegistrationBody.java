package pl.wojo.app.ecommerce_backend.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//is like LocalBodyEntity 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationBody {
    
    @NotNull
    @NotBlank
    @Size(min=3, max=255)
    private String username;
    
    @Email
    @NotBlank
    private String email;
    
    @NotNull
    @NotBlank
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,32}$")  // nie dziala nwm czm ten regex
    // @Size(min=3, max=32)
    private String password;

    @NotNull
    @NotBlank
    private String firstName;
    
    @NotNull
    @NotBlank
    private String lastName;
}
