package com.sp.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sp.app.domain.Posts;

@SpringBootTest
public class PostsRepositoryTest {
	
	@Autowired
	private PostsRepository postsRepository;
	
	@AfterEach
	public void cleanup() {
		postsRepository.deleteAll();
	}
	
	@Test
	public void execute() {
		String title  = "테스트 제목";
		String content = "내용입니다.";
		String writer = "김자바";
		String ipAddr = "127.0.0.1";
		
		Posts vo = Posts.builder()
				.title(title)
				.content(content)
				.ipAddr(ipAddr)
				.writer(writer)
				.build();
		
		postsRepository.save(vo);
		
		// writer = "자바";
		
		List<Posts> list = postsRepository.findAll();
		for(Posts dto : list) {
			assertThat(dto.getTitle()).isEqualTo(title); // 두 값 (객체 비교)
			assertThat(dto.getContent()).isEqualTo(content);
			assertThat(dto.getWriter()).isEqualTo(writer);
			assertThat(dto.getIpAddr()).isEqualTo(ipAddr);
		}
	}
		
}
