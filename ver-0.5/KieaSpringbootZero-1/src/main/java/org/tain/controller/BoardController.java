package org.tain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tain.annotation.SocialUser;
import org.tain.domain.User;
import org.tain.service.BoardService;
import org.tain.utils.CurrentInfo;

import lombok.extern.java.Log;

@Controller
@RequestMapping("/board")
@Log
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping({"", "/"})
	public String board(@RequestParam(value="id", defaultValue="0") Long id, Model model) {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		model.addAttribute("board", this.boardService.findBoardById(id));
		return "board/form";
	}
	
	@GetMapping({"/list"})
	//public String list(@PageableDefault(direction=Direction.DESC, sort="id", size=20, page=0) Pageable pageable, @SocialUser User user, Model model) {
	//public String list(@PageableDefault Pageable pageable, @SocialUser User user, Model model) {  // Page request [number: 0, size 10, sort: UNSORTED]
	public String list(Pageable pageable, @SocialUser User user, Model model) {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		model.addAttribute("boardList", this.boardService.findBoardList(pageable));
		return "board/list";
	}
}
