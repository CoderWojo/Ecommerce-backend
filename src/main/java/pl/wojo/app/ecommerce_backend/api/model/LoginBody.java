package pl.wojo.app.ecommerce_backend.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginBody {
    
    @NotNull
    @NotBlank
    private String username;
    
    @NotBlank
    @NotNull
    private String password;
}
