package www.ariandasilvaperez.spend_sense.security.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import www.ariandasilvaperez.spend_sense.security.models.User;
import www.ariandasilvaperez.spend_sense.security.models.dto.UserDTO;
import www.ariandasilvaperez.spend_sense.security.repository.IUserRepository;
import www.ariandasilvaperez.spend_sense.security.services.IUserServices;

@Service
public class UserServices implements IUserServices {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public User getCurrentUser(Authentication authentication){
        if (authentication == null){
            throw new AccessDeniedException("No user logged");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(()->{
            return new RuntimeException("User not found in the database");
        });
    }

    @Override
    public UserDTO toDTO(User user){
        return UserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
