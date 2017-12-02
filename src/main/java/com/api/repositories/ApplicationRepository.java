package com.api.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.api.entities.Application;

@Repository
public class ApplicationRepository {

	List<Application> applications = new ArrayList<>();

	public List<Application> getApplications() {
		return applications;
	}

}
