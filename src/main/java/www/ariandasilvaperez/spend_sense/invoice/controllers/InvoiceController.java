package www.ariandasilvaperez.spend_sense.invoice.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import www.ariandasilvaperez.spend_sense.invoice.models.dto.InvoiceDTO;
import www.ariandasilvaperez.spend_sense.invoice.services.IInvoiceServices;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InvoiceController {

    @Autowired
    private IInvoiceServices invoiceServices;

    @GetMapping("/invoice/{id}")
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable Long id,
                                                 Authentication authentication){
        return new ResponseEntity<>(invoiceServices.getInvoice(id, authentication), HttpStatus.OK);
    }

    @GetMapping("/invoices")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices(@RequestParam(required = false) String search,
                                                           Authentication authentication){
        List<InvoiceDTO> invoices;
        if (search != null){
            invoices = invoiceServices.getAllInvoices(search, authentication);
        }else {
            invoices = invoiceServices.getAllInvoices(authentication);
        }
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @PostMapping("/invoice")
    public ResponseEntity<InvoiceDTO> addInvoice(@RequestBody InvoiceDTO invoice,
                                                 Authentication authentication){
        return new ResponseEntity<>(invoiceServices.addInvoice(invoice, authentication), HttpStatus.CREATED);
    }

    @PostMapping("/invoice/autofill")
    public ResponseEntity<InvoiceDTO> autofillInputs(@RequestParam("imagen") MultipartFile imagen) {
        try {
            byte[] bytesImage = imagen.getBytes();
            InvoiceDTO invoice = invoiceServices.processInvoice(bytesImage);
            return new ResponseEntity<>(invoice, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/invoice/{id}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable Long id, @RequestBody InvoiceDTO invoice,
                                                    Authentication authentication){
        return new ResponseEntity<>(invoiceServices.updateInvoice(id, invoice, authentication), HttpStatus.OK);
    }

    @DeleteMapping("/invoice/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long id,
                                           Authentication authentication){
        invoiceServices.deleteInvoice(id, authentication);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}

