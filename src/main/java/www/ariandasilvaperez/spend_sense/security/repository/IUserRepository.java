package www.ariandasilvaperez.spend_sense.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import www.ariandasilvaperez.spend_sense.security.models.User;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
