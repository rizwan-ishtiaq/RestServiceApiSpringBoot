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
		m.put("Method:Enpoint", "Description");

		m.put("GET:/jobs", "List all offers");
		m.put("POST:/jobs", "Add new offer");
		m.put("GET:/jobs/{jobTitle}", "Return single offer");

		m.put("GET:/jobs/{jobTitle}/applications", "List all application on offer");
		m.put("POST:/jobs/{jobTitle}/applications", "Apply candidate application");
		m.put("GET:/jobs/{jobTitle}/applications/{candidateEmail}", "Return single application on offer for candidate");

		m.put("PUT:/jobs/{jobTitle}/applications/{candidateEmail}/change/{status}",
				"Change the status of particular job");
		return m;
	}

}
