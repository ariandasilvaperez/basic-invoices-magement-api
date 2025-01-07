package www.ariandasilvaperez.spend_sense.invoice.services;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import www.ariandasilvaperez.spend_sense.invoice.models.dto.InvoiceDTO;

import java.io.IOException;
import java.util.List;

public interface IInvoiceServices {
    @Transactional(readOnly = true)
    InvoiceDTO getInvoice(Long id, Authentication authentication);
    @Transactional(readOnly = true)
    List<InvoiceDTO> getAllInvoices(Authentication authentication);
    @Transactional(readOnly = true)
    List<InvoiceDTO> getAllInvoices(String search, Authentication authentication);
    @Transactional
    InvoiceDTO addInvoice(InvoiceDTO invoice, Authentication authentication);
    @Transactional
    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoice, Authentication authentication);
    @Transactional
    void deleteInvoice(Long id, Authentication authentication);
    InvoiceDTO processInvoice(byte[] InvoiceImage) throws IOException;
}
