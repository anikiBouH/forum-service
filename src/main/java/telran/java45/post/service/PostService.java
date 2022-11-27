package telran.java45.post.service;

import java.util.List;

import telran.java45.post.dto.CommentCreateDto;
import telran.java45.post.dto.PeriodDto;
import telran.java45.post.dto.PostCreateDto;
import telran.java45.post.dto.PostDto;


public interface PostService {
	
	PostDto addPost(String author, PostCreateDto postCreateDto);
	
	PostDto findPostById(String id);
	
	void addLike(String id);
	
	List<PostDto> findPostsByAuthor(String author);
	
	PostDto addComment(String id, String author, CommentCreateDto commentDto);
	
	PostDto deletePost(String id);
	
	List<PostDto> findPostByTags(List<String> tags);
	
	List<PostDto> findPostByPeriod(PeriodDto periodDto);
	
	PostDto updatePost(String id, PostCreateDto postCreateDto);
}
