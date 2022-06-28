package mvc.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<Object> noCustomers(CustomerNotFoundException exception){
        Error err=new Error(exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidCustomerInput.class)
    public ResponseEntity<Object> invalidInput(InvalidCustomerInput exception){
        Error err = new Error(exception.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> constraintViolationException(MethodArgumentNotValidException exception){
        String output = "";
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        for(int i=0;i<errors.size();i++){
            ObjectError temp = errors.get(i);
            String fieldName = ((FieldError)temp).getField();
            String message = temp.getDefaultMessage();
            output += fieldName + " : " + message + "\n";
        }
        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AddressNotFoundException.class)
    public ResponseEntity<Object> noAddressesFound(AddressNotFoundException exception){
        Error err = new Error(exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PhoneNotFoundException.class)
    public ResponseEntity<Object> noPhones(PhoneNotFoundException exception){
        Error err=new Error(exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }


}
