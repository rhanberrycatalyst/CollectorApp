package xmen.collectorapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Color;
import xmen.collectorapp.service.ColorService;

@Controller("colorController")
public class ColorController {

	@Autowired
	private ColorService colorService;

	@RequestMapping(value = "/color", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<Object> getAllCollectibles() {

		Response response = colorService.findAll();
		return new ResponseEntity<Object>(response.getResponseObject(), HttpStatus.OK);
	}

	@RequestMapping(value = "/color", method = RequestMethod.POST, produces = "application/json", consumes = { "application/json" })
	public @ResponseBody ResponseEntity<Object> postUser(
			@RequestBody Color color) {

		Response response = colorService.create(color);
		return new ResponseEntity<Object>(response.getResponseObject(), response.getStatusCode());
	}
	
}
