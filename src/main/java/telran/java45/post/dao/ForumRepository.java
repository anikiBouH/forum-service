package telran.java45.post.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java45.post.model.Post;

public interface ForumRepository extends CrudRepository<Post, String> {

	Stream<Post> findPostByAuthorIgnoreCase(String author);

	Stream<Post> findPostByDateCreatedBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

	@Query("[{'$unwind':'$tags'},{'tags':{$in:?0}}],{'$group':'_id'}")
	Stream<Post> findPostByTagsIn(List<String> tags);
}
