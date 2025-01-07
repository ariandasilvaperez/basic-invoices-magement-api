package www.ariandasilvaperez.spend_sense.invoice.exceptions;

public class InvoiceAlreadyExistsException extends RuntimeException{
    public InvoiceAlreadyExistsException(String message){
        super(message);
    }
}
