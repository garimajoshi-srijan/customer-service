package mvc.service;

import java.util.ArrayList;
import java.util.List;
import mvc.exception.CustomerNotFoundException;
import mvc.exception.InvalidCustomerInput;
import mvc.model.Customer;
import mvc.model.CustomerAddress;
import mvc.model.Phone;
import mvc.repository.AddressRepository;
import mvc.repository.CustomerRepository;
import mvc.repository.PhoneRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    

	@Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PhoneRepository phoneRepository;

    ModelMapper modelmapper;
    
    AddressService addressService = new AddressServiceImpl();
    PhoneService phoneService = new PhoneServiceImpl();

    public CustomerServiceImpl() {
    	modelmapper=new ModelMapper();
    	modelmapper.getConfiguration().setSkipNullEnabled(true);
        modelmapper.addMappings(new PropertyMap<Customer, Customer>() {
            @Override
            protected void configure() {
                skip(destination.getCustomerId());
            }
        });
    	}

    //TO GET ALL CUSTOMERS // NO PARAMETER REQUIRED
    @Override
    public List<Customer> getAllCustomers() {

        List<Customer> output = customerRepository.findAll();
        if (output == null || output.size() == 0) {
            throw new CustomerNotFoundException("No customers available...");
        } else {
            return output;
        }
    }

    //TO GET CUSTOMER BY USERNAME // USERNAME WILL BE PROVIDED AS A QUERY PARAMETER...
    @Override
    public Customer getCustomerByUserName(String username) {
        List<Customer> list = customerRepository.findAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserName().equals(username)) {
                return list.get(i);
            }
        }
        throw new CustomerNotFoundException("Customer with given username does not exist...");
    }

    //TO GET CUSTOMER BY FIRST AND LAST NAME // FIRST AND LAST NAME WILL BE PROVIDED AS A QUERY PARAMETER
    @Override
    public List<Customer> getCustomerByFirstNameAndLastName(String firstname, String lastname) {
        List<Customer> output = new ArrayList<>();
        List<Customer> list = customerRepository.findAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFirstName().equals(firstname) && list.get(i).getLastName().equals(lastname)) {
                output.add(list.get(i));
            }
        }
        if (output == null || output.isEmpty()) {
            throw new CustomerNotFoundException("Customer with given first name and last name does not exist...");
        } else {
            return output;
        }

    }

    //TO GET CUSTOMERS BY ID // ID WILL BE PROVIDED IN THE PATH VARIABLE
    @Override
    public Customer getCustomerById(int id) {
        if (customerRepository.existsById(id)) {
            return customerRepository.findById(id).get();
        } else {
            throw new CustomerNotFoundException("Customer with given id does not exists...");
        }
    }

    //TO CREATE CUSTOMERS // CUSTOMER WILL BE PROVIDED AS A REQUEST BODY AND CUSTOMER'S ID IS AUTO-GENERATED
    @Override
    public Customer createCustomer(Customer customer) {
        if (customer == null) {
            throw new InvalidCustomerInput("Invalid input...");
        }
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            //please change here to valid exception class
            //this actually gives other message in console if youll remove try and catch
            throw new InvalidCustomerInput(e.getLocalizedMessage());
        }
    }


    //TO UPDATE CUSTOMERS // CUSTOMER ID IS PROVIDES IN THE PATH VARIABLE AND CUSTOMER OBJECT IN THE REQUEST BODY.
    // NO NEED TO PROVIDE CUSTOMER ID AGAIN IN THE CUSTOMER'S REQUEST BODY OBJECT...
    @Override
    public Customer updateCustomerById(int id, Customer customer1) {
    	if(customer1.getCustomerId()!=0 && customer1.getCustomerId()!=id )
    		throw new CustomerNotFoundException("Customer Id Doesnt match");
    	else if (customerRepository.existsById(id)) {
            Customer existingCustomer = customerRepository.findById(id).get();
            modelmapper.map(customer1, existingCustomer);
			/*
			 * existingCustomer.setCustomerId(id); // if (customer1.getFirstName() != null)
			 * existingCustomer.setFirstName(customer1.getFirstName()); // if
			 * (customer1.getLastName() != null)
			 * existingCustomer.setLastName(customer1.getLastName()); // if
			 * (customer1.getAge() != 0) existingCustomer.setAge(customer1.getAge()); // if
			 * (customer1.getUserName() != null)
			 * existingCustomer.setUserName(customer1.getUserName()); // if
			 * (customer1.getOrganizationName() != null)
			 * existingCustomer.setOrganizationName(customer1.getOrganizationName()); // if
			 * (customer1.getIndustry() != null)
			 * existingCustomer.setIndustry(customer1.getIndustry()); // if
			 * (customer1.getGender() != null)
			 * existingCustomer.setGender(customer1.getGender());
			 */
            System.out.print(existingCustomer.toString());
            return customerRepository.save(existingCustomer);
        } else {
            throw new CustomerNotFoundException("Customer with given id does not exist...");
        }
    }

    @Override
    public void deleteCustomerById(int id) {
        if (customerRepository.existsById(id)) {
            Customer existingCustomer = customerRepository.findById(id).get();
            customerRepository.save(existingCustomer);
            if(existingCustomer.getAddressList() != null && existingCustomer.getAddressList().size() != 0){
                List<CustomerAddress> addressList = addressRepository.findAll();
                List<CustomerAddress> temp = new ArrayList<>();
                if(addressList != null) {
                    for (int i = 0; i < addressList.size(); i++) {
                        if(addressList.get(i).getCustomerId() == id){
                            temp.add(addressList.get(i));
                        }
                    }
                }
                for(int i=0;i<temp.size();i++){
                    addressRepository.deleteById(temp.get(i).getAddressId());
                }
            }
            if(existingCustomer.getPhoneList() != null && existingCustomer.getPhoneList().size() != 0){
                List<Phone> phoneList = phoneRepository.findAll();
                List<Phone> temp = new ArrayList<>();
                if(phoneList != null){
                    for(int i=0;i<phoneList.size();i++){
                        if(phoneList.get(i).getCustomerId() == id){
                           temp.add(phoneList.get(i));
                        }
                    }
                }
                for(int i=0;i<temp.size();i++){
                    phoneRepository.deleteById(temp.get(i).getPhoneId());
                }
            }
            customerRepository.deleteById(id);
        } else {
            throw new CustomerNotFoundException("Customer with given Id does not exist...");
        }
    }
}
