package mvc.exception;

public class InvalidCustomerInput extends RuntimeException{

    public InvalidCustomerInput(String msg){
        super(msg);
    }

    public InvalidCustomerInput(){

    }

}
