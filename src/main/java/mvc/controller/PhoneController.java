package mvc.controller;

import mvc.model.Phone;
import mvc.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping("/phone")
public class PhoneController {


    @Autowired
    PhoneService phoneService;

    @GetMapping("/phones")
    public List<Phone> getAllPhones(){
        return phoneService.getAllPhones();
    }

    @GetMapping("phone/{id}")
    public Phone getPhoneById(@PathVariable("id") int id){
        return phoneService.getPhoneById(id);
    }

    @PostMapping("phone")
    public Phone createPhone(@Valid @RequestBody Phone phone1){
        return phoneService.createPhone(phone1);
    }

    @PutMapping("phone/{id}")
    public Phone updatePhoneById(@PathVariable("id") int id,@Valid @RequestBody Phone phone1){
        return phoneService.updatePhoneById(id,phone1);
    }

    @DeleteMapping("phone/{id}")
    public void deletePhoneById(@PathVariable("id") int id){
        phoneService.deletePhoneById(id);
    }

    @DeleteMapping("phone/customerid/{id}")
    public void deletePhoneByCustomerId(@PathVariable("id") int id){
        phoneService.deletePhoneByCustomerId(id);
    }


}
