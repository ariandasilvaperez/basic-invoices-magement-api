package www.ariandasilvaperez.spend_sense.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import www.ariandasilvaperez.spend_sense.product.models.Product;
import www.ariandasilvaperez.spend_sense.security.models.User;

import java.util.Optional;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByUserAndId(User user, Long id);
}