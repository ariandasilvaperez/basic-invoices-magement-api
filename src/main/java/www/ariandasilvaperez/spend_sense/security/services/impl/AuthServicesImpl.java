package www.ariandasilvaperez.spend_sense.security.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import www.ariandasilvaperez.spend_sense.security.models.User;
import www.ariandasilvaperez.spend_sense.security.models.dto.TokenDTO;
import www.ariandasilvaperez.spend_sense.security.models.dto.UserDTO;
import www.ariandasilvaperez.spend_sense.security.repository.IUserRepository;
import www.ariandasilvaperez.spend_sense.security.services.IAuthServices;
import www.ariandasilvaperez.spend_sense.security.services.IJwtServices;

@Service
public class AuthServicesImpl implements IAuthServices {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IJwtServices jwtUtilityService;

    @Override
    public TokenDTO login(UserDTO login) throws Exception {
        User user = userRepository.findByEmail(login.getEmail()).orElseThrow(()->{
            return new RuntimeException("User not found");
        });

        if (!verifyPassword(login.getPassword(), user.getPassword())){
            throw new RuntimeException("Authentication failed");
        }

        return  TokenDTO.builder()
                .token(jwtUtilityService.generateJWT(user.getEmail()))
                .build();
    }

    @Override
    public UserDTO register(UserDTO user) throws Exception{
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));
        User userEntity = User.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .spended(user.getSpended())
                .build();
        User userRegistred = userRepository.save(userEntity);
        return UserDTO.builder()
                .username(userRegistred.getUsername())
                .firstName(userRegistred.getFirstName())
                .lastName(userRegistred.getLastName())
                .email(userRegistred.getEmail())
                .password(userRegistred.getPassword())
                .spended(user.getSpended())
                .build();
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }
}
