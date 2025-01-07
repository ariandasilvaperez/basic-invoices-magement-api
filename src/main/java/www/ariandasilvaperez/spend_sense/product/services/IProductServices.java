package www.ariandasilvaperez.spend_sense.product.services;

import com.mindee.product.invoice.InvoiceV4LineItem;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import www.ariandasilvaperez.spend_sense.product.models.dto.ProductDTO;
import www.ariandasilvaperez.spend_sense.invoice.models.Invoice;
import www.ariandasilvaperez.spend_sense.product.models.Product;

public interface IProductServices {
    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO product, Authentication authentication);

    @Transactional
    ProductDTO addProduct(ProductDTO product, Authentication authentication);
    @Transactional
    ProductDTO updateProduct(Long id, ProductDTO product, Authentication authentication);
    @Transactional
    void deleteProduct(Long id, Authentication authentication);

    public Product createProductFromLineItem(InvoiceV4LineItem line, Invoice invoice);
}
