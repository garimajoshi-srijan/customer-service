package mvc.service;

import mvc.exception.AddressNotFoundException;
import mvc.exception.CustomerNotFoundException;
import mvc.model.Customer;
import mvc.model.CustomerAddress;
import mvc.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CustomerService customerService;

    //TO GET ALL ADDRESSES
    @Override
    public List<CustomerAddress> getAllAddress() {
        List<CustomerAddress> addressList =  addressRepository.findAll();
        if(addressList == null || addressList.isEmpty()){
            throw new AddressNotFoundException("No Addresses are available...");
        }else{
            return addressList;
        }
    }

    //TO GET ADDRESS BY ADDRESS ID
    @Override
    public CustomerAddress getAddressById(int id) {
        if(addressRepository.existsById(id)) {
            return addressRepository.findById(id).get();
        }else{
            throw new AddressNotFoundException("No address found for this id...");
        }
    }


    //UPDATE ADDRESS FOR SPECIFIC ADDRESS ID
    @Override
    public CustomerAddress updateAddressById(int id,CustomerAddress customerAddress1){
        if(addressRepository.existsById(id)){
            CustomerAddress existingAddress = addressRepository.findById(id).get();
            existingAddress.setAddressLine1(customerAddress1.getAddressLine1());
            existingAddress.setAddressLine2(customerAddress1.getAddressLine2());
            existingAddress.setAddressLine3(customerAddress1.getAddressLine3());
            addressRepository.save(existingAddress);
            return existingAddress;
        }else{
            throw new AddressNotFoundException("Address with given id does not exist...");
        }
    }

    //DELETE ADDRESS FOR SPECIFIC ADDRESS ID
    @Override
    public void deleteAddressById(int id){
        if(addressRepository.existsById(id)){
            addressRepository.deleteById(id);
        }else{
            throw new AddressNotFoundException("No address is available with this id...");
        }
    }

    //DELETE ADDRESS FOR SPECIFIC CUSTOMER ID
    @Override
    public void deleteAddressByCustomerId(int id) {
        List<CustomerAddress> addressList = getAllAddress();
        if(addressList != null) {
            List<CustomerAddress> list = new ArrayList<>();
            for (int i = 0; i < addressList.size(); i++) {
                CustomerAddress temp = addressList.get(i);
                if (temp.getCustomerId() == id) {
                    list.add(temp);
                }
            }
            if (list.size() == 0) {
                throw new AddressNotFoundException("No Addresses are available for given customer...");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    deleteAddressById(list.get(i).getAddressId());
                }
            }
        }
    }

    //CREATE ADDRESS FOR A CUSTOMER (CUSTOMER ID IS GIVEN)
    @Override
    public CustomerAddress createCustomerAddress(CustomerAddress customerAddress1) {
        Integer temp = customerAddress1.getCustomerId();
        if(temp == null || temp == 0){
            throw new CustomerNotFoundException("Customer Id cannot be null");
        }
        else{
            try{
                Customer customer = customerService.getCustomerById(customerAddress1.getCustomerId());
                return addressRepository.save(customerAddress1);
            }catch(CustomerNotFoundException e){
                throw new CustomerNotFoundException("Customer id is invalid...");
            }
        }
    }

    //FIND CUSTOMER BY ADDRESS ID
    @Override
    public Customer getCustomerWithAddressId(int id){
        if(addressRepository.existsById(id)){
            CustomerAddress existingAddress = addressRepository.findById(id).get();
            return customerService.getCustomerById(existingAddress.getCustomerId());
        }else{
            throw new AddressNotFoundException("Address id is invalid...");
        }
    }

}

