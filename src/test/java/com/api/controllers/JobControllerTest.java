package com.api.controllers;

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
import com.api.entities.Offer;
import com.api.repositories.OfferRepository;
import com.api.services.OfferService;
import com.api.services.OfferServiceImpl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppEntryClass.class)
@WebAppConfiguration
public class JobControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private Offer offer;

	private List<Offer> offerList = new ArrayList<>();

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

		Offer o = new Offer();
		o.setJobTitle("job1");
		offerService.createNewOffer(o);
		offerList.add(o);

		o = new Offer();
		o.setJobTitle("job2");
		offerService.createNewOffer(o);
		offerList.add(o);

		offer = offerList.get(0);
	}

	/**
	 * return proper status codes for the offer not found
	 * 
	 * @throws Exception
	 */
	@Test
	public void offerNotFound() throws Exception {
		mockMvc.perform(get("/jobs/jobn").contentType(contentType)).andExpect(status().isNotFound());
	}



	/**
	 * Read single offer
	 * 
	 * @throws Exception
	 */
	@Test
	public void readSingleOffer() throws Exception {
		mockMvc.perform(get("/jobs/job1")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.jobTitle", is(offer.getJobTitle())))
				.andExpect(jsonPath("$.numberOfApplications", is(0)));
	}

	/**
	 * List Offers
	 * 
	 * @throws Exception
	 */
	@Test
	public void readOffers() throws Exception {
		mockMvc.perform(get("/jobs")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].jobTitle", is(this.offerList.get(0).getJobTitle())))
				.andExpect(jsonPath("$[0].numberOfApplications", is(0)))
				.andExpect(jsonPath("$[1].jobTitle", is(this.offerList.get(1).getJobTitle())))
				.andExpect(jsonPath("$[1].numberOfApplications", is(0)));
	}

	/**
	 * Create job offer
	 * 
	 * @throws Exception
	 */
	@Test
	public void createOffer() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle("job3");
		String bookmarkJson = toJson(offer);

		this.mockMvc.perform(post("/jobs").contentType(contentType).content(bookmarkJson))
				.andExpect(status().isCreated());
	}

	@SuppressWarnings("unchecked")
	protected String toJson(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
