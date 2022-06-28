package mvc.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
public class ToEmail {
	private String to;
	private String subject;
	//private String text="<table style=\"width:100%\">";
	public ToEmail(String to,String subject){
		this.to=to;
		this.subject=subject;
	}
	
}
