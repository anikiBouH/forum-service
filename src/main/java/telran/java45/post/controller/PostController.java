package telran.java45.post.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import telran.java45.post.dto.CommentCreateDto;
import telran.java45.post.dto.PeriodDto;
import telran.java45.post.dto.PostCreateDto;
import telran.java45.post.dto.PostDto;
import telran.java45.post.service.PostService;

@RestController
@RequiredArgsConstructor
public class PostController {

		final PostService postService;
		
		@PostMapping("/forum/post/{author}")
		public PostDto addPost(@PathVariable String author, @RequestBody PostCreateDto postCreateDto) {
			return postService.addPost(author, postCreateDto);
		}
		
		@GetMapping("/forum/post/{id}")
		public PostDto findPostById(@PathVariable String id) {
			return postService.findPostById(id);
		}
		
		@PutMapping("/forum/post/{id}/like")
		public void addLike(@PathVariable String id) {
			postService.addLike(id);
		}
		
		@GetMapping("/forum/posts/author/{author}")
		public List<PostDto> findPostByAuthor(@PathVariable String author) {
			return postService.findPostsByAuthor(author);
		}
		
		@PutMapping("/forum/post/{id}/comment/{author}")
		public PostDto addComment(@PathVariable String id,@PathVariable String author, @RequestBody CommentCreateDto commentDto) {
			return postService.addComment(id, author, commentDto);
	
		}
		
		@DeleteMapping("/forum/post/{id}")
		public PostDto deletePost(@PathVariable String id) {
			return postService.deletePost(id);
		}
		
		@PostMapping("/forum/posts/tags")
		public List<PostDto> findPostByTags(@RequestBody List<String> tags){
			return postService.findPostByTags(tags);
		}
		
		@PostMapping("/forum/posts/period")
		public List<PostDto> findPostByperiod(@RequestBody PeriodDto periodDto){
			return postService.findPostByPeriod(periodDto);
		}
		
		@PutMapping("/forum/post/{id}")
		public PostDto updatePost(@PathVariable String id, @RequestBody PostCreateDto postCreateDto) {
			return postService.updatePost(id, postCreateDto);
		}
		
}
