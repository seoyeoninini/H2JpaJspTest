package com.sp.app.service;


import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sp.app.domain.Posts;
import com.sp.app.repository.PostsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor // final 변수를 인자로 받음 - 생성자를 이용한 의존성 주입, @Autowired를 대신함
@Slf4j
public class PostsServiceImpl implements PostsService {
	private final PostsRepository postsRepository;
	
	@Override
	public void insertPosts(Posts entity) throws Exception {
		
		try {
			postsRepository.save(entity);
			
		} catch (Exception e) {
			log.info("PostsService - insertPosts : " + e);
		}
	}

	@Override
	public void updatePosts(Posts entity) throws Exception {
		try {
			// save() : 없으면 insert, 존재하면 update
			postsRepository.save(entity);
		} catch (Exception e) {
			log.info("PostsService- updatePosts : ", e);
		}
	}

	@Override
	public void deletePosts(long num) throws Exception {
		try {
			postsRepository.deleteById(num);
		} catch (Exception e) {
			log.info("PostsService- deletePosts : ", e);
		}
	}

	@Override
	public void updateHitCount(long num) throws Exception {
		try {
			Posts dto = postsRepository.findById(num).orElseThrow(() -> new IllegalAccessError("[" + num + "] 데이터가 존재하지 않습니다."));
			
			dto.setHitCount(dto.getHitCount() + 1);
			
			postsRepository.save(dto);
		
		} catch (Exception e) {
			log.info("PostsService- updateHitCount : ", e);
		}
	}
	

	@Override
	public Page<Posts> listPage(int current_page, int size) {
		Page<Posts> page = null;
		
		try {
			Pageable pageable = PageRequest.of(current_page-1, size,
					Sort.by(Sort.Direction.DESC, "num"));
			
			page = postsRepository.findAll(pageable);
		} catch (IllegalAccessError e) {
			// 게시글이 없는 경우
		} catch (Exception e) {
			log.info("PostsService-listPage:", e);
		}
		return page;
	}
	
	@Override
	public Posts findById(long num) {
		Posts dto = null;
		
		try {
			Optional<Posts> posts = postsRepository.findById(num);
			dto = posts.get();
		} catch (NoSuchElementException e) {
			// posts.get() 에서 데이터가 존재하지 않는 경우
		} catch (Exception e) {
			log.info("PostsService-findById: ", e);
		}
		return dto;
	}
	
}
