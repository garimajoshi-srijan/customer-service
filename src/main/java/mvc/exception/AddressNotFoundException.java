package mvc.exception;

public class AddressNotFoundException extends RuntimeException{

    public AddressNotFoundException(String msg){
        super(msg);
    }

    public AddressNotFoundException(){

    }

}
