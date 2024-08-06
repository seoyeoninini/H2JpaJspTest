package com.sp.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter 
@NoArgsConstructor // 인자 없는 생성자
public class Posts {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY) // 자동으로 +1 증가
	private long num;
	
	@Column(length = 500, nullable = false)
	private String title;
	
	@Column(columnDefinition = "TEXT",nullable = false)
	private String content;
	
	@Column(length = 50, nullable = false)
	private String writer;
	
	@Column(name = "ipaddr", nullable = false, length = 50, updatable = false)
	private String ipAddr;
	
	// CURRENT_TIMESTAMP : 마리아db date(오라클의 sysdate와 유사.) 
	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",
			insertable = false, updatable = false)
	private String reg_date;
	
	@Column(name = "hitcount", nullable = false, columnDefinition = "INT DEFAULT 0", insertable = false)
	private int hitCount;
	
	@Builder // 빌드 패턴 - 인자 순서를 지키지 않아도 넣을 수 있음
	public Posts(String title, String content, String writer, String ipAddr) {
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.ipAddr = ipAddr;
	}
}
