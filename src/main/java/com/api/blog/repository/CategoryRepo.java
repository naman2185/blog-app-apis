package com.api.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
