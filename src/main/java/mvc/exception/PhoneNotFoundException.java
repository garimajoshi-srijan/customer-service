package mvc.exception;

public class PhoneNotFoundException extends RuntimeException{

    public PhoneNotFoundException(String msg){
        super(msg);
    }

    public PhoneNotFoundException(){

    }

}
