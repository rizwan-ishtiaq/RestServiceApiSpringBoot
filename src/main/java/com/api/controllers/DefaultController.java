package com.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DefaultController {

	@RequestMapping(method = RequestMethod.GET)
	public List<String> showHelp() {
		List<String> l = new ArrayList<>();
		l.add("Method,Enpoint,Description");

		l.add("GET,/jobs,List all offers");
		l.add("POST,/jobs,Add new offer");
		l.add("GET,/jobs/{jobTitle},Return single offer");

		l.add("GET,/jobs/{jobTitle}/applications,List all application on offer");
		l.add("POST,/jobs/{jobTitle}/applications,Apply candidate application");
		l.add("GET,/jobs/{jobTitle}/applications/{candidateEmail},Return single application on offer for candidate");

		l.add("PUT,/jobs/{jobTitle}/applications/{candidateEmail}/change/{status},Change the status of particular job");
		return l;
	}

}
