package mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import mvc.exception.AddressNotFoundException;
import mvc.exception.PhoneNotFoundException;
import mvc.model.Customer;
import mvc.model.CustomerAddress;
import mvc.model.Phone;
import mvc.model.View;
import mvc.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
@RestController
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;
    //TO GET ALL CUSTOMERS OR BY FIELD
    @JsonView(View.CustomerView.class)
    @GetMapping("/customers")
    public List<Customer> getAllCustomers(@RequestParam (required = false) Integer id,
                                          @RequestParam (required = false) String username,
                                          @RequestParam (required = false) String firstname,
                                          @RequestParam (required = false) String lastname){
        if(id != null){
            Customer obj = customerService.getCustomerById(id);
            List<Customer> list = new ArrayList<>();
            list.add(obj);
            return list;
        }else if(username != null){
            Customer obj = customerService.getCustomerByUserName(username);
            List<Customer> list = new ArrayList<>();
            list.add(obj);
            return list;
        }else if(firstname != null && lastname != null){
            return customerService.getCustomerByFirstNameAndLastName(firstname,lastname);
        }

        return customerService.getAllCustomers();

    }


    //TO GET CUSTOMERS BY ID
    @JsonView(View.CustomerView.class)
    @GetMapping("customer/{id}")
    public Customer getCustomerById(@PathVariable int id){
        Customer customer = customerService.getCustomerById(id);
        return customer;
    }

    //UPDATE CUSTOMER
    @JsonView(View.CustomerView.class)
    @PutMapping("/customer/{id}")
    public Customer updateCustomerById(@PathVariable int id, @RequestBody Customer customer1){
        return customerService.updateCustomerById(id,customer1);
    }

    //CREATE CUSTOMER
    @JsonView(View.CustomerView.class)
    @PostMapping("/customer")
    public Customer createCustomer(@Valid @RequestBody Customer customer1){
        return customerService.createCustomer(customer1);
    }

    //DELETE CUSTOMERS
    @DeleteMapping("/customer/{id}")
    public void deleteCustomerById(@PathVariable int id){
        customerService.deleteCustomerById(id);
    }

    @GetMapping("customer/{id}/addresses")
    public List<CustomerAddress> getCustomerAddressWithCustomerId(@PathVariable int id){
        List<CustomerAddress> temp = customerService.getCustomerById(id).getAddressList();
        if(temp == null || temp.size()==0){
            throw new AddressNotFoundException("No addresses are available for this customer...");
        }
        return temp;
    }

    @GetMapping("customer/{id}/phoneNumbers")
    public List<Phone> getPhoneWithCustomerService(@PathVariable("id") int id){
        List<Phone> temp = customerService.getCustomerById(id).getPhoneList();
        if(temp == null || temp.size()==0){
            throw new PhoneNotFoundException("No phone numbers are available for this customer...");
        }
        return temp;
    }

}
