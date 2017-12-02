package com.api.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.api.entities.Offer;

@Repository
public class OfferRepository {

	private List<Offer> offers = new ArrayList<>();

	public List<Offer> getOffers() {
		return offers;
	}

}
