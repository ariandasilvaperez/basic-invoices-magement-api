package www.ariandasilvaperez.spend_sense.product.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import www.ariandasilvaperez.spend_sense.invoice.models.dto.InvoiceDTO;
import www.ariandasilvaperez.spend_sense.security.models.dto.UserDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Long id;
    private String name;
    private int quantity;
    private double price;
    @JsonIgnore
    private UserDTO user;
    private InvoiceDTO invoice;
}
