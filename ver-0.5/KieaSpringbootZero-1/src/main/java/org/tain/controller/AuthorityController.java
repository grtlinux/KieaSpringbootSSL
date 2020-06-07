package org.tain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tain.utils.CurrentInfo;

import lombok.extern.java.Log;

@RestController
@Log
public class AuthorityController {

	@GetMapping("/facebook")
	public String facebook() {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		return "facebook";
	}
	
	@GetMapping("/google")
	public String google() {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		return "google";
	}
	
	@GetMapping("/kakao")
	public String kakao() {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		return "kakao";
	}
}
