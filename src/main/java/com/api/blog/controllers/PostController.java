package com.api.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.config.Constants;
import com.api.blog.payloads.ApiResponse;
import com.api.blog.payloads.PostDto;
import com.api.blog.payloads.PostResponse;
import com.api.blog.payloads.UserDto;
import com.api.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/user/{user_id}/category/{category_id}/post/")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer user_id, @PathVariable Integer category_id){
		PostDto addePostDto = this.postService.createPost(postDto, user_id, category_id);
		return new ResponseEntity<PostDto>(addePostDto, HttpStatus.CREATED);
	}
	// get all pot by user
	@GetMapping("/user/{user_id}/post/")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer user_id,
			 @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			 @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize){
		PostResponse postResponse = this.postService.getPostsByUser(user_id, pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	//get all post by category
	@GetMapping("/category/{category_id}/post/")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer category_id,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize){
		PostResponse postResponse = this.postService.getPostsByCategory(category_id, pageNumber, pageSize);//ctrl + 1 + enter
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/post/{postId}/")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	//get all post
	@GetMapping("/post/")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIR, required = false) String sortDir){
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
		// pageNumber starts with 0
	}
	
	@PutMapping("/post/{postId}/")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{postId}/")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
	}
	
	// search posts by keywords
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostsByKeyword(@PathVariable("keyword") String keyword){
		List<PostDto> postsByKeyword = this.postService.searchPostsByKeyword(keyword);
		return new ResponseEntity<List<PostDto>>(postsByKeyword, HttpStatus.OK);
	}
}
