package com.api.blog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.blog.entities.Category;
import com.api.blog.entities.Post;
import com.api.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	Page<Post> findByUser(User user, Pageable p);
	Page<Post> findByCategory(Category category, Pageable p);
	//List<Post> findByTitle(String title);
	List<Post> findByTitleContaining(String title);// It will run a query using 'like'
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByKeywordInTitle(@Param("key") String title); // It is same as above

}
