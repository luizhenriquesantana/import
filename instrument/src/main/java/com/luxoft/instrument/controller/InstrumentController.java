package com.luxoft.instrument.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.luxoft.instrument.service.InstrumentService;
import com.luxoft.instrument.text.TextFormat;
import com.luxoft.instrument.to.JSONObject;

@RestController
@RequestMapping("/v1")
public class InstrumentController {

	private static final Logger LOG = LoggerFactory.getLogger(InstrumentController.class);

	@Autowired
	private InstrumentService service;

	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
	private String upload(@RequestBody JSONObject data) throws Exception {
		LOG.trace("Entering left(data={})", data);
		String message = null;
		try {
			if (data != null) {
				service.buildReaderFromRequest(data.getData());
			} else {
				throw new Exception("The request data is null.");
			}
			message = buildJsonResponse("Import finished. Please, check the logs for more information.");
		} catch (Exception e) {
			message = buildJsonResponse(TextFormat.formatMessage(
					"{} ocurred during the import. The message was: '{}'. Please, check the logs for more information.",
					e.getClass().getSimpleName(), e.getMessage()));
			e.printStackTrace();
		}

		LOG.trace("Leaving left(id, data)={}", message);
		return message;
	}

	/**
	 * 
	 * Just create a successful message.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique
	 *         Santana</a>.
	 * @param message
	 *            to be included on the json response
	 * @return the successful message. String
	 */
	private String buildJsonResponse(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"");
		sb.append("message");
		sb.append("\":");
		sb.append("\"");
		sb.append(message);
		sb.append("\"");
		sb.append("}");
		return sb.toString();
	}
	
}
