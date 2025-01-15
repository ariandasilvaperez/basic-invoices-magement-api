package www.ariandasilvaperez.spend_sense.security.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import www.ariandasilvaperez.spend_sense.security.exceptions.UserNotFoundException;
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
            return new UserNotFoundException("User not found in the database");
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

    @Override
    public UserDTO updateUser(Long id, UserDTO user){
        User userExists = userRepository.findById(id).orElseThrow(()->{
            return new UserNotFoundException("User not found with id" + id);
        });

        if (user.getFirstName() != null){
            userExists.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null){
            userExists.setLastName(user.getLastName());
        }

        if (user.getUsername() != null){
            userExists.setUsername(user.getUsername());
        }

        if (user.getEmail() != null){
            userExists.setEmail(user.getEmail());
        }

        if (user.getSpended() != null){
            userExists.setSpended(user.getSpended());
        }

        return toDTO(userRepository.save(userExists));
    }
}
