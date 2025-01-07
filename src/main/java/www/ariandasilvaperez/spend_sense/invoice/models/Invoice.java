package www.ariandasilvaperez.spend_sense.invoice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import www.ariandasilvaperez.spend_sense.product.models.Product;
import www.ariandasilvaperez.spend_sense.security.models.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false,unique = true)
    private Long id;
    @Column(name = "supplier_name", nullable = false)
    private String supplierName;
    @Column(name = "date", nullable = false, columnDefinition = "DATE")
    private LocalDate date;
    @Column(name = "invoice_number", nullable = false,unique = true)
    private String invoiceNumber;
    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference(value = "invoice-product")
    private List<Product> products = new ArrayList<>();

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}