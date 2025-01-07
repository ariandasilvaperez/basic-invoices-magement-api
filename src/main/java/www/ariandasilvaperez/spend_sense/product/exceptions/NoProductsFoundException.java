package www.ariandasilvaperez.spend_sense.product.exceptions;

public class NoProductsFoundException extends RuntimeException{
    public NoProductsFoundException(String message){
        super(message);
    }
}
