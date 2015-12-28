package xmen.collectorapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.dao.ColorDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Color;
import xmen.collectorapp.util.Errors;
import xmen.collectorapp.util.Validator;

@Service("colorService")
public class ColorService {

	@Autowired
	private ColorDAO colorDAO;

	public Response findAll() {
		Response response = new Response();
		findAllColors(response);
		return response;
	}

	public Response create(Color color) {
		Response response = new Response();
		persist(color, response);
		return response;
	}
	
public Map<String, ArrayList<String> > ValidateFields(Color color) {
		
		Map<String, ArrayList<String> > errors = new HashMap<String, ArrayList<String> >();
		Validator.validateString(errors, "name", color.getName(), 50, java.util.EnumSet.of(Validator.StringValidationOptions.NOT_EMPTY,Validator.StringValidationOptions.LOWER_CASE_ONLY)) ;
		return errors;
	}

	@Transactional
	private void findAllColors(Response response) {
		try {
			response.setResponseObject(colorDAO.findAll());
			response.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			response.setStatusCode(HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	private void persist(Color color, Response response) {
		try {
			
			Map<String, ArrayList<String> > errors = ValidateFields(color);
			if (errors.size() >0)
			{
				response.setResponseObject(errors);
				response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
				return;
			}
			
			colorDAO.save(color);
			response.setResponseObject(color);
			response.setStatusCode(HttpStatus.CREATED);
		} catch (PersistenceException e) {
			ArrayList<String> responseErrors = new ArrayList<String>();
			responseErrors.add(Errors.convertDatabaseExceptionToERRMessage(e) );

			response.setResponseObject(responseErrors);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
