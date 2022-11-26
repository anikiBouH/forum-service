package telran.java45.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.ToString;
import telran.java45.post.model.Comment;

@ToString
public class PostDto {
	String id;
    String title;
    String content;
    String author;
    LocalDateTime dateCreated;
    List<String> tags;
    int likes;
    List<Comment> comments;
    
}
