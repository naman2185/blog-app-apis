package com.api.blog.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private Integer id;
	private String content;
	private UserDto userDto;
	private PostDto postDto;
	
}
