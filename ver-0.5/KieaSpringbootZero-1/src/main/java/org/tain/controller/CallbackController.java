package org.tain.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tain.object.RequestCallback;
import org.tain.object.ResponseCallback;

import lombok.extern.java.Log;

@RestController
@RequestMapping(value = "/v1")
@Log
public class CallbackController {

	@RequestMapping(method = RequestMethod.POST, path = "/remittances.callback")
	public ResponseCallback callback(@RequestBody RequestCallback requestCallback) {
		log.info("KANG-20200608 >>>>> " + requestCallback);
		return ResponseCallback.builder()
				.message("ACKNOWLEDGE")
				.status("success")
				.build();
	}
}
