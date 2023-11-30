package com.api.blog.services;

import com.api.blog.payloads.CommentDto;

public interface CommentService {
	
	//create comment
	CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);
	
	//delete Comment
	void deleteComment(Integer commentId);
	

}
