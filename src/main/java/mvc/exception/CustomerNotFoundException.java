package mvc.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String msg){
        super(msg);
    }
    public CustomerNotFoundException(){

    }

}
