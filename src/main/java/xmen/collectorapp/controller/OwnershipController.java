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
import xmen.collectorapp.entity.Ownership;
import xmen.collectorapp.service.OwnershipService;

@Controller("ownershipController")
public class OwnershipController {

	@Autowired
	private OwnershipService ownershipService;

	@RequestMapping(value = "/ownership", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<Object> getAllCollectibles() {

		Response response = ownershipService.findAll();
		return new ResponseEntity<Object>(response.getResponseObject(), HttpStatus.OK);
	}

	@RequestMapping(value = "/ownership", method = RequestMethod.POST, produces = "application/json", consumes = { "application/json" })
	public @ResponseBody ResponseEntity<Object> postUser(
			@RequestBody Ownership ownership) {

		Response response = ownershipService.create(ownership);
		return new ResponseEntity<Object>(response.getResponseObject(), response.getStatusCode());
	}
	
}
