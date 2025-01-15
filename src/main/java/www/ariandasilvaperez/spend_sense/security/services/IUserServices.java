package www.ariandasilvaperez.spend_sense.security.services;

import org.springframework.security.core.Authentication;
import www.ariandasilvaperez.spend_sense.security.models.User;
import www.ariandasilvaperez.spend_sense.security.models.dto.UserDTO;

public interface IUserServices {
    User getCurrentUser(Authentication authentication);

    UserDTO toDTO(User user);

    UserDTO updateUser(Long id, UserDTO user);
}
