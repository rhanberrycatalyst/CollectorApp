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
import xmen.collectorapp.entity.Category;
import xmen.collectorapp.service.CategoryService;

@Controller("categoryController")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/category", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<Object> getAllCategories() {

		Response response = categoryService.findAllCategory();

		return new ResponseEntity<Object>(response.getResponseObject(), HttpStatus.OK);
	}

	@RequestMapping(value = "/category", method = RequestMethod.POST, produces = "application/json", consumes = { "application/json" })
	public @ResponseBody ResponseEntity<Object> postUser(
			@RequestBody Category category) {

		Response response = categoryService.create(category);

		return new ResponseEntity<Object>(response.getResponseObject(), response.getStatusCode());
	}
}
