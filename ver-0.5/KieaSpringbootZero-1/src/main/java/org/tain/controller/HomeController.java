package org.tain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tain.utils.CurrentInfo;

import lombok.extern.java.Log;

@Controller
@RequestMapping({"/"})
@Log
public class HomeController {

	@GetMapping("")
	public String home() {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		return "home";
	}
}
