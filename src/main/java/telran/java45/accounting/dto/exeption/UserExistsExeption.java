package telran.java45.accounting.dto.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistsExeption extends RuntimeException {

	private static final long serialVersionUID = 4824811555806207671L;
	
	public UserExistsExeption(String login) {
		super("User " + login + " exists");
	}

}
