package com.challenge.challengeSCB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import com.challenge.challengeSCB.bean.request.Request;
import com.challenge.challengeSCB.bean.response.ReturnObj;
import com.challenge.challengeSCB.exception.ValidationException;
import com.challenge.challengeSCB.service.ChallengeService;

@RestController
@RequestMapping("")
public class ChallengeController {

	@Autowired
	ChallengeService challengeService;

	@PostMapping(value = "/challengescb")
	public ReturnObj challenge(@RequestBody Request request) {

		return challengeService.work(request);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handlerValidation(ValidationException ex) {
		return ex.getMessage();
	}

}
