package www.ariandasilvaperez.spend_sense.invoice.exceptions;

public class InvoiceNotFoundException extends RuntimeException{
    public InvoiceNotFoundException(String message){
        super(message);
    }
}
