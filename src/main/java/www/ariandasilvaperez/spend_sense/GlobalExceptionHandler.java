package www.ariandasilvaperez.spend_sense;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import www.ariandasilvaperez.spend_sense.invoice.exceptions.InvoiceAlreadyExistsException;
import www.ariandasilvaperez.spend_sense.invoice.exceptions.InvoiceNotFoundException;
import www.ariandasilvaperez.spend_sense.invoice.exceptions.NoInvoicesFoundException;
import www.ariandasilvaperez.spend_sense.product.exceptions.NoProductsFoundException;
import www.ariandasilvaperez.spend_sense.product.exceptions.ProductAlreadyExistsException;
import www.ariandasilvaperez.spend_sense.product.exceptions.ProductNotFoundException;
import www.ariandasilvaperez.spend_sense.security.exceptions.NoUsersFoundException;
import www.ariandasilvaperez.spend_sense.security.exceptions.UserAlreadyExistsException;
import www.ariandasilvaperez.spend_sense.security.exceptions.UserNotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    /*====================================PRODUCT Exceptions====================================*/
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NoProductsFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoProductsFoundException(NoProductsFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleProductAlreadyExistsException(ProductAlreadyExistsException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /*====================================PRODUCT Exceptions====================================*/
    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleInvoiceNotFoundException(InvoiceNotFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NoInvoicesFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoInvoicesFoundException(NoInvoicesFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvoiceAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleInvoiceAlreadyExistsException(InvoiceAlreadyExistsException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /*====================================USER Exceptions====================================*/
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> habdleUserNotFoundException(UserNotFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoUsersFoundException(NoUsersFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



    /*====================================GENERIC Exceptions====================================*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
