package org.tain.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tain.config.Env;

@RestController
@RequestMapping(value = "/info")
public class EmployeeController {

	@RequestMapping(value = "")
	public String index() {
		String message = String.format("Hello, world by 강석 at %s, [SERVER_NAME=%s]"
				, new Date()
				, Env.getServerName());
		return message;
	}
}
