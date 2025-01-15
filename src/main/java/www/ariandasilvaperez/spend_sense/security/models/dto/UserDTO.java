package www.ariandasilvaperez.spend_sense.security.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    @NotEmpty(message = "Email cant be empty or null")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "Password cant be empty or null")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$", message = "Invalid password format")
    private String password;
    private Double spended;
}
