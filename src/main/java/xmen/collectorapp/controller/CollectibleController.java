package xmen.collectorapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Collectible;
import xmen.collectorapp.service.CollectibleService;

@Controller("collectibleController")
public class CollectibleController {

	@Autowired
	private CollectibleService collectibleService;

	@RequestMapping(value = "/collectible", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<Object> getAllCollectibles() {

		Response response = collectibleService.findAll();
		return new ResponseEntity<Object>(response.getResponseObject(), response.getStatusCode());
	}
	
	@RequestMapping(value = "/collectible", method = RequestMethod.POST, produces = "application/json", consumes = { "application/json" })
	public @ResponseBody ResponseEntity<Object> postUser( @RequestBody Collectible collectible) {

		Response response = collectibleService.create(collectible);
		return new ResponseEntity<Object>(response.getResponseObject(), response.getStatusCode());
	}

}
