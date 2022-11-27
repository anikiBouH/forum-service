package telran.java45.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostDto {
	String id;
    String title;
    String content;
    String author;
    LocalDateTime dateCreated;
    List<String> tags;
    int likes;
    List<CommentDto> comments;
    
}
