package com.sp.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sp.app.domain.Posts;

// <entity, 기본키>
public interface PostsRepository extends JpaRepository<Posts, Long> {
	
}
