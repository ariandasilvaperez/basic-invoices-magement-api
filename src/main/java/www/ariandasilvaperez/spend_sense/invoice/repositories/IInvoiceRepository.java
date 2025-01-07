package www.ariandasilvaperez.spend_sense.invoice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import www.ariandasilvaperez.spend_sense.invoice.models.Invoice;
import www.ariandasilvaperez.spend_sense.security.models.User;

import java.util.List;
import java.util.Optional;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT i FROM Invoice i WHERE i.user = :user AND " +
            "(i.supplierName LIKE %:search% OR i.invoiceNumber LIKE %:search%)")
    List<Invoice> findAllBySearchTerm(@Param("user") User user, @Param("search") String search);
    List<Invoice> findAllByUser(User user);
    Optional<Invoice> findByUserAndId(User user, Long id);
    Boolean existsByUserAndInvoiceNumber(User user, String invoiceNumber);
}
