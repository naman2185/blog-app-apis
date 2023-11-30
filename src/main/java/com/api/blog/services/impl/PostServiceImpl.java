package com.api.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.blog.entities.Category;
import com.api.blog.entities.Post;
import com.api.blog.entities.User;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.payloads.PostDto;
import com.api.blog.payloads.PostResponse;
import com.api.blog.repository.CategoryRepo;
import com.api.blog.repository.PostRepo;
import com.api.blog.repository.UserRepo;
import com.api.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer user_id, Integer category_id) {
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setDate(new Date());
		
		User user = this.userRepo.findById(user_id)
				.orElseThrow(()-> new ResourceNotFoundException("User", "user_id", user_id));
		Category category = this.categoryRepo.findById(category_id)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "category_id", category_id));
		post.setCategory(category);
		post.setUser(user);
		
		Post addedPost = this.postRepo.save(post);
		return this.modelMapper.map(addedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer post_id) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(post_id)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post_id", post_id));
		if(StringUtils.isNotBlank(postDto.getTitle())) {
			post.setTitle(postDto.getTitle());
		}
		if(StringUtils.isNotBlank(postDto.getContent())) {
			post.setContent(postDto.getContent());
		}
		if(StringUtils.isNotBlank(postDto.getImageName())) {
			post.setImageName(postDto.getImageName());
		}
		if(postDto.getDate()!=null) {
			post.setDate(postDto.getDate());
		}
		if(postDto.getCategory()!=null) {
			post.setCategory(this.modelMapper.map(postDto.getCategory(), Category.class));
		}
		
		if(postDto.getUser()!=null) {
			post.setUser(this.modelMapper.map(postDto.getUser(), User.class));
		}
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer post_id) {
		Post post = this.postRepo.findById(post_id)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post_id", post_id));
		this.postRepo.delete(post);
		
	}

	@Override
	public PostDto getPostById(Integer post_id) {
		Post post = this.postRepo.findById(post_id)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post_id", post_id));	
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		//Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> content = pagePost.getContent();
		//List<Post> list = this.postRepo.findAll();
		List<PostDto> postDtos = content.stream()
				.map(lst-> this.modelMapper.map(lst, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getNumberOfElements());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostsByUser(Integer user_id, Integer pageNumber, Integer pageSize) {
		User user=this.userRepo.findById(user_id)
				.orElseThrow(()-> new ResourceNotFoundException("User", "user_id", user_id));
		Pageable p = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost = this.postRepo.findByUser(user, p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getNumberOfElements());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostResponse getPostsByCategory(Integer category_id, Integer pageNumber, Integer pageSize) {
		Category category = this.categoryRepo.findById(category_id)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "category_id", category_id));
		Pageable p = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost = this.postRepo.findByCategory(category, p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream()
				.map((post)-> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getNumberOfElements());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> searchPostsByKeyword(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepo.searchByKeywordInTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map(post-> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

}
