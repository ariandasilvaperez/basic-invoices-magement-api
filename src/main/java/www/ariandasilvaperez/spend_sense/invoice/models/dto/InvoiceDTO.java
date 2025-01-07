package www.ariandasilvaperez.spend_sense.invoice.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import www.ariandasilvaperez.spend_sense.product.models.dto.ProductDTO;
import www.ariandasilvaperez.spend_sense.security.models.dto.UserDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDTO {
    private Long id;
    private String supplierName;
    private LocalDate date;
    private String invoiceNumber;
    private double totalAmount;
    @JsonIgnore
    private UserDTO user;
    private List<ProductDTO> products = new ArrayList<>();
}