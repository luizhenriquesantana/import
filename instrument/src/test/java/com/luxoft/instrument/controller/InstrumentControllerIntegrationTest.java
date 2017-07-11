package com.luxoft.instrument.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.luxoft.instrument.Entity.InstrumentPriceModifier;
import com.luxoft.instrument.repository.InstrumentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InstrumentControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	public InstrumentRepository repository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	/**
	 * Initial setup
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		this.mvc = webAppContextSetup(webApplicationContext).build();
		this.repository.deleteAll();
	}

	/**
	 * Validate the multiplier for the USDPLEXRATE
	 * @throws Exception
	 */
	@Test
	public void testUSDPLEXRATE() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("//v1/upload").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"VVNEUExFWFJBVEUsMDctanVsLTIwMTcsNC4wMA0KVVNEUExFWFJBVEUsMDgtanVsLTIwMTcsNC4wMQ0KVVNEUExFWFJBVEUsMDktanVsLTIwMTcsNC4wMg0KVVNEUExFWFJBVEUsMTAtanVsLTIwMTcsNC4wMg0KQlJMVE9aTE9UWSwwNy1qdWwtMjAxNyw0LjAwDQpCUkxUT1pMT1RZLDA4LWp1bC0yMDE3LDQuMDENCkJSTFRPWkxPVFksMDktanVsLTIwMTcsNC4wMg0KQlJMVE9aTE9UWSwxMC1qdWwtMjAxNyw0LjAyDQpaTE9UWVRPVVNELDA3LWp1bC0yMDE3LDQuMDANClpMT1RZVE9VU0QsMDgtanVsLTIwMTcsNC4wMQ0KWkxPVFlUT1VTRCwwOS1qdWwtMjAxNyw0LjAyDQpaTE9UWVRPVVNELDEwLWp1bC0yMDE3LDQuMDI=\"" + "}")).andExpect(status().isOk()).andReturn();
		InstrumentPriceModifier base = repository.findByName("USDPLEXRATE");
		Assert.assertThat(base.getMultiplier(), Matchers.is(Double.parseDouble("16.08")));
	}
	
	/**
	 * Validate the multiplier for the BRLTOZLOTY
	 * @throws Exception
	 */
	@Test
	public void testBRLTOZLOTY() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("//v1/upload").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"VVNEUExFWFJBVEUsMDctanVsLTIwMTcsNC4wMA0KVVNEUExFWFJBVEUsMDgtanVsLTIwMTcsNC4wMQ0KVVNEUExFWFJBVEUsMDktanVsLTIwMTcsNC4wMg0KVVNEUExFWFJBVEUsMTAtanVsLTIwMTcsNC4wMg0KQlJMVE9aTE9UWSwwNy1qdWwtMjAxNyw0LjAwDQpCUkxUT1pMT1RZLDA4LWp1bC0yMDE3LDQuMDENCkJSTFRPWkxPVFksMDktanVsLTIwMTcsNC4wMg0KQlJMVE9aTE9UWSwxMC1qdWwtMjAxNyw0LjAyDQpaTE9UWVRPVVNELDA3LWp1bC0yMDE3LDQuMDANClpMT1RZVE9VU0QsMDgtanVsLTIwMTcsNC4wMQ0KWkxPVFlUT1VTRCwwOS1qdWwtMjAxNyw0LjAyDQpaTE9UWVRPVVNELDEwLWp1bC0yMDE3LDQuMDI=\"" + "}")).andExpect(status().isOk()).andReturn();
		InstrumentPriceModifier base = repository.findByName("BRLTOZLOTY");
		Assert.assertThat(base.getMultiplier(), Matchers.is(Double.parseDouble("16.08")));
	}
	
	/**
	 * Validate the multiplier for the ZLOTYTOUSD.
	 * @throws Exception
	 */
	@Test
	public void testZLOTYTOUSD() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("//v1/upload").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"VVNEUExFWFJBVEUsMDctanVsLTIwMTcsNC4wMA0KVVNEUExFWFJBVEUsMDgtanVsLTIwMTcsNC4wMQ0KVVNEUExFWFJBVEUsMDktanVsLTIwMTcsNC4wMg0KVVNEUExFWFJBVEUsMTAtanVsLTIwMTcsNC4wMg0KQlJMVE9aTE9UWSwwNy1qdWwtMjAxNyw0LjAwDQpCUkxUT1pMT1RZLDA4LWp1bC0yMDE3LDQuMDENCkJSTFRPWkxPVFksMDktanVsLTIwMTcsNC4wMg0KQlJMVE9aTE9UWSwxMC1qdWwtMjAxNyw0LjAyDQpaTE9UWVRPVVNELDA3LWp1bC0yMDE3LDQuMDANClpMT1RZVE9VU0QsMDgtanVsLTIwMTcsNC4wMQ0KWkxPVFlUT1VTRCwwOS1qdWwtMjAxNyw0LjAyDQpaTE9UWVRPVVNELDEwLWp1bC0yMDE3LDQuMDI=\"" + "}")).andExpect(status().isOk()).andReturn();
		InstrumentPriceModifier base = repository.findByName("ZLOTYTOUSD");
		Assert.assertThat(base.getMultiplier(), Matchers.is(Double.parseDouble("16.08")));
	}
}
