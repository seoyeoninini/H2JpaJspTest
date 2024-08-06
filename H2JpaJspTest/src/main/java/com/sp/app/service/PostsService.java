package com.sp.app.service;

import org.springframework.data.domain.Page;

import com.sp.app.domain.Posts;

public interface PostsService {
	public void insertPosts(Posts entity) throws Exception;
	public void updatePosts(Posts entity) throws Exception;
	public void deletePosts(long num) throws Exception;
	
	public void updateHitCount(long num) throws Exception;
	
	public Page<Posts> listPage(int current_page, int size);
	public Posts findById(long num);
	
}
