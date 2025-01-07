package www.ariandasilvaperez.spend_sense.invoice.exceptions;

public class NoInvoicesFoundException extends RuntimeException{
    public NoInvoicesFoundException(String message){
        super(message);
    }
}
