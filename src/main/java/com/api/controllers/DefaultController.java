package com.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DefaultController {

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, String> showHelp() {
		Map<String, String> m = new HashMap<>();
		m.put("Enpoint", "Method, Description");

		m.put("/jobs", "GET, List all offers");
		m.put("/jobs", "POST, Add new offer");
		m.put("/jobs/{jobTitle}", "GET, Return single offer");

		m.put("/jobs/{jobTitle}/applications", "GET, List all application on offer");
		m.put("/jobs/{jobTitle}/applications", "POST, Apply candidate application");
		m.put("/jobs/{jobTitle}/applications/{candidateEmail}",
				"GET, Return single application on offer for candidate");

		m.put("/jobs/{jobTitle}/applications/{candidateEmail}/change/{status}",
				"PUT, Change the status of particular job");
		return m;
	}

}
