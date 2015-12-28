package xmen.collectorapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.dao.KeywordDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Keyword;
import xmen.collectorapp.util.Errors;
import xmen.collectorapp.util.Validator;

@Service("KeywordService")
public class KeywordService {

	@Autowired
	private KeywordDAO keywordDAO;

	public Response findAll() {
		Response response = new Response();
		findAllKeywords(response);
		return response;
	}
	
	public Map<String, ArrayList<String> > ValidateFields(Keyword keyword ) {
		
		Map<String, ArrayList<String> > errors = new HashMap<String, ArrayList<String> >();
		
		Validator.validateString(errors, "name", keyword.getName(), 50, java.util.EnumSet.of(Validator.StringValidationOptions.NOT_EMPTY, Validator.StringValidationOptions.LOWER_CASE_ONLY )) ;

		return errors;
	}

	
	public Response create(Keyword keyword) {
		Response response = new Response();
	
		Map<String, ArrayList<String> > errors = ValidateFields(keyword);
		if (errors.size() >0)
		{
			response.setResponseObject(errors);
			response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
			return response;
		}
		
		persist(keyword, response);
		
		return response;
	}
	
	
	public Response update(Keyword keyword) {
		Response response = new Response();
	
		Map<String, ArrayList<String> > errors = ValidateFields(keyword);
		if (errors.size() >0)
		{
			response.setResponseObject(errors);
			response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
			return response;
		}
		
		update(keyword, response);
		
		return response;
	}
	

	@Transactional
	private void findAllKeywords(Response response) {
		try {
			response.setResponseObject(keywordDAO.findAll());
			response.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			response.setStatusCode(HttpStatus.NOT_FOUND);
		}
	}

	
	@Transactional
	private void persist(Keyword keyword, Response response) {
		try {
			keywordDAO.save(keyword);
			response.setResponseObject(keyword);
			response.setStatusCode(HttpStatus.CREATED);
		} 
		catch (PersistenceException e) {
			ArrayList<String> responseErrors = new ArrayList<String>();
			responseErrors.add(Errors.convertDatabaseExceptionToERRMessage(e) );
			
			response.setResponseObject(responseErrors);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Transactional
	private void update(Keyword keyword, Response response) {
		try {
			keywordDAO.update(keyword);
			response.setResponseObject(keyword);
			response.setStatusCode(HttpStatus.CREATED);
		} 
		catch (PersistenceException e) {
			ArrayList<String> responseErrors = new ArrayList<String>();
			responseErrors.add(Errors.convertDatabaseExceptionToERRMessage(e) );
			
			response.setResponseObject(responseErrors);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
