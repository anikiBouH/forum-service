package telran.java45.post.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java45.post.dao.ForumRepository;
import telran.java45.post.dto.CommentCreateDto;
import telran.java45.post.dto.PeriodDto;
import telran.java45.post.dto.PostCreateDto;
import telran.java45.post.dto.PostDto;
import telran.java45.post.dto.exeption.PostNotFoundExeption;
import telran.java45.post.model.Comment;
import telran.java45.post.model.Post;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {

	final ForumRepository forumRepository;
	final ModelMapper modelMapper;

	@Override
	public PostDto addPost(String author, PostCreateDto postCreateDto) {
		Post post = modelMapper.map(postCreateDto, Post.class);
		post.setAuthor(author);
		post.setDateCreated(LocalDateTime.now());
		post.setComments(new ArrayList<Comment>());
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto findPostById(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundExeption());
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public void addLike(String id) {
		if (forumRepository.findById(id).isPresent()) {
			Post post = forumRepository.findById(id).orElse(null);
			post.setLikes(post.getLikes() + 1);
			forumRepository.save(post);
		}
	}

	@Override
	public PostDto addComment(String id, String author, CommentCreateDto commentDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundExeption());
		Comment comment = Comment.builder().user(author).message(commentDto.getMessage())
				.dateCreated(LocalDateTime.now()).likes(0).build();
		post.getComments().add(comment);

		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto deletePost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundExeption());
		forumRepository.deleteById(id);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatePost(String id, PostCreateDto postCreateDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundExeption());

		if (postCreateDto.getTitle() != null) {
			post.setTitle(postCreateDto.getTitle());
		}
		if (postCreateDto.getContent() != null) {
			post.setContent(postCreateDto.getContent());
		}
		if (postCreateDto.getTags() != null) {
			post.getTags().addAll(postCreateDto.getTags());
		}
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> findPostByPeriod(PeriodDto periodDto) {
		LocalDateTime dateFrom = LocalDateTime.of(periodDto.getDateFrom(), LocalTime.MIN);
		LocalDateTime dateTo = LocalDateTime.of(periodDto.getDateTo(), LocalTime.MAX);
		return forumRepository.findPostByDateCreatedBetween(dateFrom, dateTo)
				.map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> findPostsByAuthor(String author) {
		List<PostDto> postsByAuthor = forumRepository.findPostByAuthorIgnoreCase(author)
				.map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsByAuthor;
	}
	
	@Override
	public List<PostDto> findPostByTags(List<String> tags) {
		return forumRepository.findPostByTagsInIgnoreCase(tags)
				.map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}


}
