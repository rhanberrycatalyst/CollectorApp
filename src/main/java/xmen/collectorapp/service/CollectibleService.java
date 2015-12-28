package xmen.collectorapp.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.EnumSet;

import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.dao.CollectibleDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Collectible;
import xmen.collectorapp.util.Errors;
import xmen.collectorapp.util.Validator;

@Service("collectibleService")
public class CollectibleService{
	
	Logger logger = Logger.getLogger(CollectibleService.class);
	
	@Autowired
	private CollectibleDAO collectibleDAO;

	public Response findAll() {
		Response response = new Response();
		findAllCollectibles(response);
		return response;
	}
	
	public Map<String, ArrayList<String> > ValidateFields(Collectible collectible ) {
		
		Map<String, ArrayList<String> > errors = new HashMap<String, ArrayList<String> >();

		Validator.validateString(errors, "name", collectible.getName(), 255, java.util.EnumSet.of(Validator.StringValidationOptions.NOT_EMPTY)) ;
	    Validator.validateString(errors, "description", collectible.getDescription(), 1000, EnumSet.noneOf(Validator.StringValidationOptions.class)  ) ;
	    Validator.validateString(errors, "catalogNumber", collectible.getCatalogueNumber(), 16, EnumSet.of(Validator.StringValidationOptions.CATALOG_NUMBER_FORMAT)  ) ;

		return errors;
	}

	public Response create(Collectible collectible) {
		Response response = new Response();
	
		Map<String, ArrayList<String> > errors = ValidateFields(collectible);
		if (errors.size() >0)
		{
			response.setResponseObject(errors);
			response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
			return response;
		}
		
		persist(collectible, response);
		
		return response;
	}
	
	
	@Transactional
	private void findAllCollectibles(Response response) {
		try {
			response.setResponseObject(collectibleDAO.findAll());
			response.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			response.setStatusCode(HttpStatus.NOT_FOUND);
		}
	}
	

	@Transactional
	private void persist(Collectible collectible, Response response) {
		try {
			collectibleDAO.save(collectible);
			response.setResponseObject(  collectibleDAO.findCollectibleByID(collectible.getId()) );
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
