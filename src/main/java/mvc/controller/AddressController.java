package mvc.controller;
import mvc.model.Customer;
import mvc.model.CustomerAddress;
import mvc.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    //TO GET ALL ADDRESSES
    @GetMapping("/addresses")
    public List<CustomerAddress> getAllAddress(){
        return addressService.getAllAddress();
    }

    //TO GET ADDRESS BY ID
    @GetMapping("address/{id}")
    public CustomerAddress getAddressById(@PathVariable int id){
        return addressService.getAddressById(id);
    }


    //TO GET CUSTOMER NAME BY ADDRESS ID
    @GetMapping("address/{id}/customer")
    public Customer getCustomerWithAddressId(@PathVariable("id") int id){
        return addressService.getCustomerWithAddressId(id);
    }

    //CREATE ADDRESS(CUSTOMER ID IN BODY)
    @PostMapping("/address")
    public CustomerAddress createAddress(@Valid @RequestBody CustomerAddress customerAddress1){
        return addressService.createCustomerAddress(customerAddress1);
    }

    //UPDATE ADDRESS FOR SPECIFIC ADDRESS ID
    @PutMapping("address/{id}")
    public CustomerAddress updateCustomerById(@PathVariable int id,@Valid @RequestBody CustomerAddress customerAddress1){
        return addressService.updateAddressById(id,customerAddress1);
    }

    //DELETE ADDRESS FOR SPECIFIC ADDRESS ID
    @DeleteMapping("address/{id}")
    public void deleteAddressById(@PathVariable("id") int id){
        addressService.deleteAddressById(id);
    }

    //DELETE ADDRESS FOR SPECIFIC CUSTOMER ID
    @DeleteMapping("address/customerid/{id}")
    public void deleteAddressByCustomerId(@PathVariable("id") int id){
        addressService.deleteAddressByCustomerId(id);
    }

}
