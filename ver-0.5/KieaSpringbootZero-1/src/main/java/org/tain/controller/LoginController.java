package org.tain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.tain.utils.CurrentInfo;

import lombok.extern.java.Log;

@Controller
@Log
public class LoginController {

	@GetMapping({"/login"})
	public String login() {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		return "login";
	}
	
	@GetMapping({"/loginSuccess"})
	public String loginSuccess() {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		return "redirect:/board/list";
	}
}
