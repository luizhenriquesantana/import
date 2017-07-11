package com.luxoft.instrument.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luxoft.instrument.Entity.InstrumentPriceModifier;
import com.luxoft.instrument.repository.InstrumentRepository;

@Service
public class InstrumentService {

	@Autowired
	public InstrumentRepository repository;

	private static final Logger LOG = LoggerFactory.getLogger(InstrumentService.class);
	
	/**
	 * 
	 * Read the file from the path and build the BufferedReader input for reading lines.
	 * It is called from unit tests.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param path  of the file.
	 * void
	 */
	public void buildReaderFromFilePath(String path) {
		LOG.trace("Entering buildReaderFromFilePath(path={})", path);
		FileReader fr = null;
		BufferedReader bufferedReader = null;
		try {
			fr = new FileReader(path);
			bufferedReader = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		importInstruments(bufferedReader);
		LOG.trace("Leaving buildReaderFromFilePath(path)");
	}
	
	/**
	 * 
	 * Read the file from base64 request and build the BufferedReader input for reading lines.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param data on base64 format sent via REST.
	 * void
	 */
	public void buildReaderFromRequest(String data) {
		LOG.trace("Entering buildReaderFromRequest(data={})", data);
		BufferedReader bfReader = null;
		try {

			byte[] arr = Base64.decodeBase64(data.getBytes());
			InputStream is = null;

			is = new ByteArrayInputStream(arr);
			bfReader = new BufferedReader(new InputStreamReader(is));

		} catch (Exception e) {
			e.printStackTrace();
		}
		importInstruments(bfReader);
		LOG.trace("Leaving buildReaderFromRequest(data)");
	}
	
	/**
	 * 
	 * Read the BufferedReader line by line and process the lines for inserting
	 * the instruments on the database according to requirements.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param reader of a BufferedReader instance for reading line by line.
	 *            void
	 */
	public void importInstruments(BufferedReader reader) {
		LOG.trace("Entering importInstruments(reader={})", reader);

		//Variables used to get the values from the line
		String instrumentName = null;
		String nonFormatedDate = null;
		String nonFormatedValue = null;
		
		Date date = null;
		Double value = null;

		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			String strLine = reader.readLine();

			//Read File line by line
			while (strLine != null) {
				if (strLine != null) { // the last readLine returns null
					Boolean hasFails = Boolean.FALSE;
					String[] parts = strLine.split(",");
					if (parts.length <= 2) {
						hasFails = Boolean.TRUE;
						LOG.warn("The line '{}' does not have enough values.", strLine);
					} else {
						if (StringUtils.isNotEmpty(parts[0])) {
							instrumentName = parts[0];
						}
						if (StringUtils.isNotEmpty(parts[1])) {
							nonFormatedDate = parts[1];
							date = df.parse(nonFormatedDate);

							calendar.setTime(date);
							if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
									|| (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))) {
								LOG.warn("The line '{}' does not have a business date value.", strLine);
								hasFails = Boolean.TRUE;
							}
						}
						if (!hasFails) {
							if (StringUtils.isNotEmpty(parts[2])) {
								nonFormatedValue = parts[2];
								value = Double.parseDouble(nonFormatedValue);
							}
							
							this.save(instrumentName, value);
							LOG.info("The line '{}' was succesfuly imported.", strLine);
						}
					}
					strLine = reader.readLine();
				}
			}

		} catch (IOException e) {
			LOG.error("The file was not found. Please inform a correct path and try again.");
		} catch (ParseException e) {
			LOG.error("Couldn't parse the value.");
		}
		LOG.trace("Leaving importInstruments(reader)");
	}
	
	/**
	 * 
	 * Persist the object in the repository
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param instrumentName
	 * @param multiplier for calculate or inserting a new one
	 * @return the persisted object
	 * InstrumentPriceModifier
	 */
	public InstrumentPriceModifier save(String instrumentName, double multiplier){
		InstrumentPriceModifier instrument = repository.findByName(instrumentName);
		if(instrument != null){
			LOG.debug("The instrument '{}' was found on database. Will apply the multiplier.", instrumentName);
			instrument.setMultiplier(instrument.getMultiplier() * multiplier);
		}else{
			LOG.debug("The instrument '{}' not found on database. Creating a new register.", instrumentName);
			instrument = new InstrumentPriceModifier(instrumentName, multiplier);
		}
		return repository.save(instrument);
	}
}