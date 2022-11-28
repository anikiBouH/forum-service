package telran.java45.post.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Post {
	String id;
    String title;
    String content;
    String author;
    LocalDateTime dateCreated;
    List<String> tags;
    int likes;
    List<Comment> comments;
}
