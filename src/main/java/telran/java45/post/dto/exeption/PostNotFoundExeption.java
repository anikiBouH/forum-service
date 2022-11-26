package telran.java45.post.dto.exeption;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PostNotFoundExeption extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 9013944423172067823L;

	public PostNotFoundExeption(int id) {
		super("Post with id " + id + " not found");
	}
}
