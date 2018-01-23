package com.api.controllers;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.AppEntryClass;
import com.api.entities.Application;
import com.api.entities.Offer;
import com.api.repositories.OfferRepository;
import com.api.services.OfferService;
import com.api.services.OfferServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppEntryClass.class)
@WebAppConfiguration
public class JobApplicationControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private MockMvc mockMvc;
	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private OfferService offerService;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void eachSetup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		OfferRepository offerRepo = new OfferRepository();

		((OfferServiceImpl) offerService).setOfferRepo(offerRepo);

		offerService.createNewOffer(new Offer("job1"));
	}

	/**
	 * candidate has to be able to apply for an offer
	 * 
	 * @throws Exception
	 */
	@Test
	public void applyApplication() throws Exception {
		Application application = new Application();
		application.setCandidateEmail("rizwan@test.com");
		String bookmarkJson = toJson(application);

		this.mockMvc.perform(post("/jobs/job1/applications").contentType(contentType).content(bookmarkJson))
				.andExpect(status().isCreated());
	}

	/**
	 * return proper status codes for the application not found
	 * 
	 * @throws Exception
	 */
	@Test
	public void applicationNotFound() throws Exception {
		mockMvc.perform(get("/jobs/job1/applications/user@not.exist").contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@SuppressWarnings("unchecked")
	protected String toJson(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
