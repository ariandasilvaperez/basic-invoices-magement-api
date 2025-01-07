package www.ariandasilvaperez.spend_sense.product.services.impl;

import com.mindee.product.invoice.InvoiceV4LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.ariandasilvaperez.spend_sense.invoice.exceptions.InvoiceNotFoundException;
import www.ariandasilvaperez.spend_sense.product.exceptions.ProductNotFoundException;
import www.ariandasilvaperez.spend_sense.invoice.repositories.IInvoiceRepository;
import www.ariandasilvaperez.spend_sense.product.models.dto.ProductDTO;
import www.ariandasilvaperez.spend_sense.invoice.models.dto.InvoiceDTO;
import www.ariandasilvaperez.spend_sense.invoice.models.Invoice;
import www.ariandasilvaperez.spend_sense.product.models.Product;
import www.ariandasilvaperez.spend_sense.product.repositories.IProductRepository;
import www.ariandasilvaperez.spend_sense.product.services.IProductServices;
import www.ariandasilvaperez.spend_sense.security.models.User;
import www.ariandasilvaperez.spend_sense.security.models.dto.UserDTO;
import www.ariandasilvaperez.spend_sense.security.services.IUserServices;

@Service
public class ProductServiceImpl implements IProductServices {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserServices userServices;
    @Autowired
    IInvoiceRepository invoiceRepository;

    @Override
    public ProductDTO toDTO(Product product){
        UserDTO user = UserDTO.builder()
                .username(product.getUser().getUsername())
                .firstName(product.getUser().getFirstName())
                .lastName(product.getUser().getLastName())
                .email(product.getUser().getEmail())
                .password(product.getUser().getPassword())
                .build();
        InvoiceDTO invoice = InvoiceDTO.builder()
                .id(product.getInvoice().getId())
                .supplierName(product.getInvoice().getSupplierName())
                .invoiceNumber(product.getInvoice().getInvoiceNumber())
                .totalAmount(product.getInvoice().getTotalAmount())
                .date(product.getInvoice().getDate())
                .build();
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .invoice(invoice)
                .build();
    }

    @Override
    public Product toEntity(ProductDTO product, Authentication authentication){
        User user = userServices.getCurrentUser(authentication);
        Invoice invoice = invoiceRepository.findByUserAndId(user, product.getInvoice().getId()).orElseThrow(()->{
            return new InvoiceNotFoundException("Invoice not found with id: " + product.getInvoice().getId());
        });
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .invoice(invoice)
                .user(user)
                .build();
    }

    @Transactional
    @Override
    public ProductDTO addProduct(ProductDTO product, Authentication authentication){
        User user = userServices.getCurrentUser(authentication);
        Product productEntity = toEntity(product, authentication);
        Product productSaved = productRepository.save(productEntity);

        Invoice invoice = productEntity.getInvoice();
        invoice.setTotalAmount(invoice.getTotalAmount() + productEntity.getPrice());
        invoiceRepository.save(invoice);
        return toDTO(productSaved);
    }

    @Transactional
    @Override
    public ProductDTO updateProduct(Long id, ProductDTO product, Authentication authentication){
        User user = userServices.getCurrentUser(authentication);
        Product productExists = productRepository.findByUserAndId(user, id).orElseThrow(() -> {
            return new ProductNotFoundException("Product not found with id: " + id);
        });

        if(product.getName()!= null){
            productExists.setName(product.getName());
        }

        if(product.getQuantity()!= 0){
            productExists.setQuantity(product.getQuantity());
        }

        if(product.getPrice()!= 0){
            productExists.setPrice(product.getPrice());
        }

        return toDTO(productRepository.save(productExists));
    }

    @Transactional
    @Override
    public void deleteProduct(Long id, Authentication authentication){
        User user = userServices.getCurrentUser(authentication);
        Product productExists = productRepository.findByUserAndId(user, id).orElseThrow(()->{
            return new ProductNotFoundException("Product not found with id: " + id);
        });

        productRepository.delete(productExists);
    }

    @Override
    public Product createProductFromLineItem(InvoiceV4LineItem line, Invoice invoice) {
        Product product = new Product();
        product.setName(line.getDescription());
        product.setQuantity(line.getQuantity() != null ? line.getQuantity().intValue() : 0);
        product.setPrice(line.getTotalAmount());
        product.setInvoice(invoice);
        return product;
    }
}
