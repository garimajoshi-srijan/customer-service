package mvc.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mvc.service.EmailServiceImplement;

@RestController
public class MailServiceController {
	
	  @Autowired
	    private EmailServiceImplement emailService;
	    @GetMapping("/customers/mail")
	    public ResponseEntity Customers(@RequestParam String sendto,@RequestParam String subject){
	    	try {
				emailService.sendSimpleMessage(sendto,subject);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
	        return ResponseEntity.ok("Success");
	    }

}
