package www.ariandasilvaperez.spend_sense.invoice.services.impl;

import com.mindee.MindeeClient;
import com.mindee.input.LocalInputSource;
import com.mindee.parsing.common.PredictResponse;
import com.mindee.product.invoice.InvoiceV4;
import com.mindee.product.invoice.InvoiceV4Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.ariandasilvaperez.spend_sense.invoice.exceptions.InvoiceAlreadyExistsException;
import www.ariandasilvaperez.spend_sense.invoice.exceptions.InvoiceNotFoundException;
import www.ariandasilvaperez.spend_sense.invoice.exceptions.NoInvoicesFoundException;
import www.ariandasilvaperez.spend_sense.invoice.models.dto.InvoiceDTO;
import www.ariandasilvaperez.spend_sense.product.models.dto.ProductDTO;
import www.ariandasilvaperez.spend_sense.invoice.models.Invoice;
import www.ariandasilvaperez.spend_sense.product.models.Product;
import www.ariandasilvaperez.spend_sense.invoice.repositories.IInvoiceRepository;
import www.ariandasilvaperez.spend_sense.invoice.services.IInvoiceServices;
import www.ariandasilvaperez.spend_sense.product.services.IProductServices;
import www.ariandasilvaperez.spend_sense.invoice.services.ITempFilePersistence;
import www.ariandasilvaperez.spend_sense.security.models.User;
import www.ariandasilvaperez.spend_sense.security.models.dto.UserDTO;
import www.ariandasilvaperez.spend_sense.security.services.IUserServices;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements IInvoiceServices {
    @Autowired
    private IInvoiceRepository invoiceRepository;
    @Autowired
    private IProductServices productServices;
    private static final String API_KEY = "f39fd04bdce36d8b6579a4324dc69dce";
    @Autowired
    private ITempFilePersistence tempFilePersistenceService;
    @Autowired
    private IUserServices userServices;

    private InvoiceDTO toDTO(Invoice invoice){
        UserDTO user = UserDTO.builder()
                .username(invoice.getUser().getUsername())
                .firstName(invoice.getUser().getFirstName())
                .lastName(invoice.getUser().getLastName())
                .email(invoice.getUser().getEmail())
                .password(invoice.getUser().getPassword())
                .build();
        List<ProductDTO> products = invoice.getProducts().stream().map(product -> {
            return ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .build();
        }).toList();
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .supplierName(invoice.getSupplierName())
                .totalAmount(invoice.getTotalAmount())
                .date(invoice.getDate())
                .user(user)
                .products(products)
                .build();
    }

    private Invoice toEntity(InvoiceDTO invoice, Authentication authentication) {
        User user = userServices.getCurrentUser(authentication);
        Invoice invoiceEntity = Invoice.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .supplierName(invoice.getSupplierName())
                .totalAmount(invoice.getTotalAmount())
                .date(invoice.getDate())
                .user(user)
                .build();
        List<Product> products = invoice.getProducts().stream().map(product -> {
            return Product.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .invoice(invoiceEntity)
                    .user(user)
                    .build();
        }).collect(Collectors.toList());

        invoiceEntity.setProducts(products);
        return invoiceEntity;
    }

    @Transactional(readOnly = true)
    @Override
    public InvoiceDTO getInvoice(Long id, Authentication autentication){
        User user = userServices.getCurrentUser(autentication);
        Invoice invoice = invoiceRepository.findByUserAndId(user, id).orElseThrow(
                () -> new InvoiceNotFoundException("Invoice not found with id: " + id));
        return toDTO(invoice);
    }

    @Transactional(readOnly = true)
    @Override
    public List<InvoiceDTO> getAllInvoices(Authentication authentication){
        User user = userServices.getCurrentUser(authentication);
        List<InvoiceDTO> invoices = invoiceRepository.findAllByUser(user).stream().map(this::toDTO).toList();
        if (invoices.isEmpty()){
            throw new NoInvoicesFoundException("No invoices found in data base");
        }

        return invoices;
    }

    @Transactional(readOnly = true)
    @Override
    public List<InvoiceDTO> getAllInvoices(String search, Authentication authentication){
        User user = userServices.getCurrentUser(authentication);
        List<InvoiceDTO> invoices = invoiceRepository.findAllBySearchTerm(user, search)
                .stream().map(this::toDTO).toList();
        if (invoices.isEmpty()){
            throw new NoInvoicesFoundException("No invoices found in data base");
        }

        return invoices;
    }

    @Transactional
    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoice, Authentication authentication) {
        User user = userServices.getCurrentUser(authentication);
        Double userSpended = user.getSpended();

        Boolean invoiceExists = invoiceRepository.existsByUserAndInvoiceNumber(user, invoice.getInvoiceNumber());
        if (invoiceExists){
            throw new InvoiceAlreadyExistsException("Invoice with this invoice number already exists");
        }

        Invoice invoiceEntity = toEntity(invoice, authentication);
        Invoice invoiceSaved = invoiceRepository.save(invoiceEntity);
        userSpended = invoiceSaved.getTotalAmount() + userSpended;
        user.setSpended(userSpended);
        userServices.updateUser(user.getId(), userServices.toDTO(user));
        return toDTO(invoiceSaved);
    }
    
    @Transactional
    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoice, Authentication authentication){
        User user = userServices.getCurrentUser(authentication);
        Invoice invoiceExists = invoiceRepository.findByUserAndId(user, id).orElseThrow( () ->{
            return new InvoiceNotFoundException("Invoice not found with id " + id);
        });

        if (invoice.getInvoiceNumber() != null){
            invoiceExists.setInvoiceNumber(invoice.getInvoiceNumber());
        }

        if (invoice.getSupplierName() != null){
            invoiceExists.setSupplierName(invoice.getSupplierName());
        }

        if (invoice.getTotalAmount() != 0){
            invoiceExists.setTotalAmount(invoice.getTotalAmount());
        }

        return toDTO(invoiceRepository.save(invoiceExists));
    }

    @Transactional
    @Override
    public void deleteInvoice(Long id, Authentication authentication){
        User user = userServices.getCurrentUser(authentication);
        Double userSpended = user.getSpended();
        Invoice invoiceExists = invoiceRepository.findByUserAndId(user, id).orElseThrow( () ->{
            return new InvoiceNotFoundException("Invoice not found with id " + id);
        });

        invoiceRepository.delete(invoiceExists);
        userSpended = invoiceExists.getTotalAmount() - userSpended;
        user.setSpended(userSpended);
        userServices.updateUser(user.getId(), userServices.toDTO(user));
    }


    @Transactional
    @Override
    public InvoiceDTO processInvoice(byte[] InvoiceImage) throws IOException {
        MindeeClient mindeeClient = new MindeeClient(API_KEY);
        Path tempFile = tempFilePersistenceService.createTempFile(InvoiceImage);

        try {
            PredictResponse<InvoiceV4> response = parseInvoice(mindeeClient, tempFile);
            Invoice invoice = createInvoiceFromResponse(response);
            return toDTO(invoice);
        } finally {
            tempFilePersistenceService.deleteTempFile(tempFile);
        }
    }

    private PredictResponse<InvoiceV4> parseInvoice(MindeeClient mindeeClient, Path tempFile) throws IOException {
        LocalInputSource inputSource = new LocalInputSource(tempFile.toFile());
        return mindeeClient.parse(InvoiceV4.class, inputSource);
    }

    private Invoice createInvoiceFromResponse(PredictResponse<InvoiceV4> response) {
        Invoice invoice = new Invoice();
        InvoiceV4Document prediction = response.getDocument().getInference().getPrediction();

        invoice.setSupplierName(prediction.getSupplierName().getValue());
        invoice.setDate(prediction.getDate().getValue());
        invoice.setInvoiceNumber(prediction.getInvoiceNumber().getValue());
        invoice.setTotalAmount(prediction.getTotalAmount().getValue());
        
        List<Product> products = prediction.getLineItems().stream()
                .map(line -> productServices.createProductFromLineItem(line, invoice))
                .collect(Collectors.toList());
        invoice.setProducts(products);
        return invoice;
    }
}
