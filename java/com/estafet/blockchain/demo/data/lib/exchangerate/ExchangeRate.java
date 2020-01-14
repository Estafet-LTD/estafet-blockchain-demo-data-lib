package com.estafet.blockchain.demo.data.lib.exchangerate;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.client.RestTemplate;

import com.estafet.demo.commons.lib.properties.PropertyUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRate {

	private String currency;
	
	private Double rate;

	public String getCurrency() {
		return currency;
	}

	ExchangeRate setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public Double getRate() {
		return rate;
	}

	ExchangeRate setRate(Double rate) {
		this.rate = rate;
		return this;
	}
	
	/*
	@SuppressWarnings("rawtypes")
	public static List<ExchangeRate> getExchangeRates() {
		List objects = new RestTemplate().getForObject(PropertyUtils.instance().getProperty("PROJECT_API_SERVICE_URI") + "/projects",
				List.class);
		List<Project> projects = new ArrayList<Project>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects) {
			Project project = mapper.convertValue(object, new TypeReference<Project>() {
			});
			projects.add(project);
		}
		return projects;
	}
	*/
}
