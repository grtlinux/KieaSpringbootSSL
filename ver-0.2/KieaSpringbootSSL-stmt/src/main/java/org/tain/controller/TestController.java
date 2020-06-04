package org.tain.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tain.utils.CurrentInfo;

import lombok.extern.java.Log;

@Controller
@RequestMapping(value = "/test")
@Log
public class TestController {

	@GetMapping(value = {"", "/home"})
	public String home(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", "Kiea 강석");
		map.put("date", LocalDateTime.now());
		modelMap.put("data", map);
		
		return "test/home";
	}
}
