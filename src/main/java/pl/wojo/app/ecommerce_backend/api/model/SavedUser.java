package pl.wojo.app.ecommerce_backend.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//is like LocalBodyEntity 
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavedUser {
    
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
