package com.api.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private int categoryId;
	
	@NotEmpty
	@Size(min = 4, message = "Title must be of atleast 4 cahracers")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min = 4,max=25, message = "Description must be of atmost 425 cahracers")
	private String categoryDescription;
}
