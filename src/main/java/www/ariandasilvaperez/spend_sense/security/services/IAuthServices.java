package www.ariandasilvaperez.spend_sense.security.services;

import www.ariandasilvaperez.spend_sense.security.models.User;
import www.ariandasilvaperez.spend_sense.security.models.dto.TokenDTO;
import www.ariandasilvaperez.spend_sense.security.models.dto.UserDTO;

public interface IAuthServices {
    TokenDTO login(UserDTO login) throws Exception;
    UserDTO register(UserDTO user) throws Exception;
}
