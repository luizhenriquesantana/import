package com.luxoft.instrument.service;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxoft.instrument.Entity.InstrumentPriceModifier;
import com.luxoft.instrument.repository.InstrumentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InstrumentServiceTest {

	@InjectMocks
	private InstrumentService service;

	@Mock
	public InstrumentRepository repository;
	
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Testing if a new object was created
	 * @throws Exception
	 */
	@Test
	public void saveNew() throws Exception {
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(InstrumentPriceModifier.class));
		InstrumentPriceModifier instrument = service.save("INSTRUMENT_TEST", 57.8);
		Assert.assertThat(instrument.getName(), Matchers.is("INSTRUMENT_TEST"));
		Assert.assertThat(instrument.getMultiplier(), Matchers.is(Double.parseDouble("57.8")));
	}
}