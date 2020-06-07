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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class HomeController {

	@GetMapping(value = "")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", "Kiea");
		map.put("date", LocalDateTime.now());

		modelAndView.setViewName("home");
		modelAndView.addObject("data", map);
		
		return modelAndView;
	}
	
	@GetMapping(value = "/2")
	public String home2(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", "Kiea");
		map.put("date", LocalDateTime.now());
		modelMap.put("data", map);

		return "home";
	}
}
