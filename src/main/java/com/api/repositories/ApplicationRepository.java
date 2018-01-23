package com.api.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.api.entities.Application;

@Repository
public class ApplicationRepository {

	List<Application> applications = new ArrayList<>();

	public List<Application> getApplications() {
		return applications;
	}

	public List<Application> findByCandidateEmail(String candidateEmail) {
		return applications.stream().filter(a -> a.getCandidateEmail().equalsIgnoreCase(candidateEmail))
				.collect(Collectors.toList());
	}

}
