package com.sp.app.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp.app.domain.Posts;
import com.sp.app.service.PostsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts/*")
public class PostsController {
	private final PostsService postsService;
	
	@RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(
			@RequestParam(name = "page", defaultValue = "1") int current_page,
			Model model) throws Exception {
		
		try {
			int total_page = 0;
			int size = 5;
			long dataCount = 0;
			List<Posts> list = null;
			
			Page<Posts> pagePosts = postsService.listPage(current_page, size);
			
			if(! pagePosts.isEmpty()) {
				// 데이터가 존재하면
				total_page = pagePosts.getTotalPages();
				
				if(current_page > total_page) {
					current_page = total_page;
					pagePosts = postsService.listPage(current_page, size);
				}
				
				dataCount = pagePosts.getTotalElements();
				
				list = pagePosts.getContent();
				for(Posts dto : list) {
					dto.setReg_date(dto.getReg_date().substring(0, 10));
				}
				
			} else {
				current_page = 0;
			}
			
			model.addAttribute("list", list);
			model.addAttribute("page", current_page);
			model.addAttribute("dataCount", dataCount);
			model.addAttribute("size", size);
			model.addAttribute("total_page", total_page);
			
		} catch (Exception e) {
			log.info("PostsController-list:", e);
		}
		
		return "posts/list";
	}
	
	@GetMapping("write")
	public String writeForm(Model model) throws Exception {
		model.addAttribute("mode", "write");
		return "posts/write";
	}

	@PostMapping("write")
	public String writeSubmit(Posts dto, HttpServletRequest req) throws Exception {
		try {
			dto.setIpAddr(req.getRemoteAddr());
			postsService.insertPosts(dto);
		} catch (Exception e) {
			log.info("PostsController-writeSubmit : " + e);
		}
		
		return "redirect:/posts/list";
	}
	
	@GetMapping("article/{num}")
	public String article(
			@PathVariable(name = "num") long num,
			@RequestParam(name = "page") String page,
			Model model) throws Exception {
		
		String query = "page=" + page;
		
		postsService.updateHitCount(num);
		
		Posts dto = postsService.findById(num);
		if(dto == null) {
			return "redirect:/posts/list?" + query;
		}
		
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		
		return "posts/article";
	}
	
	@GetMapping("update/{num}")
	public String updateForm(@PathVariable(name = "num") long num,
			@RequestParam(name = "page") String page,
			Model model) throws Exception {
		
		Posts dto = postsService.findById(num);
		if(dto == null) {
			return "redirect:/posts/list?page=" + page;
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("mode", "update");
		return "posts/write";
	}

	@PostMapping("update")
	public String updateSubmit(Posts dto, @RequestParam(name = "page") String page) throws Exception {
		try {
			postsService.updatePosts(dto);
		} catch (Exception e) {
			log.info("PostsController-updateSubmit : " + e);
		}
		
		return "redirect:/posts/list?page=" + page;
	}
	
	@GetMapping("delete/{num}")
	public String delete(@PathVariable(name = "num") long num,
			@RequestParam(name = "page") String page,
			Model model) throws Exception {
		
		String query = "page=" + page;

		try {
			postsService.deletePosts(num);
		} catch (Exception e) {
		}
		
		return "redirect:/posts/list?" + query;
	}
	
	
}
