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
import xmen.collectorapp.entity.Condition;
import xmen.collectorapp.service.ConditionService;

@Controller("conditionController")
public class ConditionController {

	@Autowired
	private ConditionService conditionService;

	@RequestMapping(value = "/condition", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<Object> getAllConditions() {

		Response response = conditionService.findAll();
		return new ResponseEntity<Object>(response.getResponseObject(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/condition", method = RequestMethod.POST, produces = "application/json", consumes = { "application/json" })
	public @ResponseBody ResponseEntity<Object> postUser(
			@RequestBody Condition condition) {

		Response response = conditionService.create(condition);
		return new ResponseEntity<Object>(response.getResponseObject(), response.getStatusCode());
	}
}