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
import xmen.collectorapp.entity.Keyword;
import xmen.collectorapp.service.KeywordService;

@Controller("keywordController")
public class KeywordController {

	@Autowired
	private KeywordService keywordService;

	@RequestMapping(value = "/keyword", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<Object> getAllKeywords() {

		Response response = keywordService.findAll();
		return new ResponseEntity<Object>(response.getResponseObject(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/keyword", method = RequestMethod.POST, produces = "application/json", consumes = { "application/json" })
	public @ResponseBody ResponseEntity<Object> postKeyword(
			@RequestBody Keyword keyword) {

		Response response = keywordService.create(keyword);
		return new ResponseEntity<Object>(response.getResponseObject(), response.getStatusCode());
	}
	
	@RequestMapping(value = "/keyword", method = RequestMethod.PUT, produces = "application/json", consumes = { "application/json" })
	public @ResponseBody ResponseEntity<Object> putKeyword(
			@RequestBody Keyword keyword) {

		Response response = keywordService.update(keyword);
		return new ResponseEntity<Object>(response.getResponseObject(), response.getStatusCode());
	}
	
	
	
}