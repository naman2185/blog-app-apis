package com.api.blog.services;

import java.util.List;

import com.api.blog.entities.Post;
import com.api.blog.payloads.PostDto;
import com.api.blog.payloads.PostResponse;

public interface PostService {

	//create
	PostDto createPost(PostDto postDto, Integer user_id, Integer category_id);
	
	//update
	PostDto updatePost(PostDto postDto, Integer post_id);
	
	//delete
	void deletePost(Integer post_id);
	
	//get
	PostDto getPostById(Integer post_id);
	
	//getAll
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//getAllPostByUser
	PostResponse getPostsByUser(Integer user_id, Integer pageNumber, Integer pageSize);
	
	//getAllPostByCategory
	PostResponse getPostsByCategory(Integer category_id, Integer pageNumber, Integer pageSize);
	
	//search all post by Keyword
	List<PostDto> searchPostsByKeyword(String keyword);
}
