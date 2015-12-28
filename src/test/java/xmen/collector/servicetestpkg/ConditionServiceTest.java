package xmen.collector.servicetestpkg;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.isA;

import java.util.ArrayList;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import xmen.collectorapp.dao.ConditionDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Condition;
import xmen.collectorapp.service.ConditionService;


@RunWith(MockitoJUnitRunner.class)
public class ConditionServiceTest {

	@Mock
	private ConditionDAO conditionDAO;
	@InjectMocks
	private ConditionService conditionService;
	
	@Before
	public void setUp() throws Exception {
		ArrayList<Condition> conditions = new ArrayList<Condition>();

		Condition c = new Condition();
		c.setName("good");
		conditions.add(c);
		conditions.add(new Condition());
		conditions.add(new Condition());
		conditions.add(new Condition());
		when(conditionDAO.findAllConditions()).thenReturn(conditions); 
	}

	@Test
	public void whenFindAll() {
		HttpStatus result = conditionService.findAll().getStatusCode();
		assertEquals(result,HttpStatus.OK );
		@SuppressWarnings("unchecked")
		ArrayList<Condition>  condition = (ArrayList<Condition> ) conditionService.findAll().getResponseObject();

		assertEquals(4, condition.size() );
	}
	
	
	@Test
	public void whenCreatingConditionTooLong() {
		Condition c= new Condition();
		c.setName("busted 0123456789001234567890012345678900123456789001234567890");
		Response response = conditionService.create(c);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingConditionEmpty() {
		Condition c = new Condition();
		c.setName("");
		Response response = conditionService.create(c);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingConditionWithUpperCase() {
		Condition c = new Condition();
		c.setName("BUSTED");
		Response response = conditionService.create(c);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingConditionWithNonLetters() {
		Condition c = new Condition();
		c.setName("busted!");
		Response response = conditionService.create(c);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingValidCondtion() {
		Condition c = new Condition();
		c.setName("busted");
		Response response = conditionService.create(c);

		assertEquals(HttpStatus.CREATED, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingDuplicateCondtion() {
		// Created dummy exception to be throw on Condition.save
		// mainly to increase code test coverage
		RuntimeException e2 = new RuntimeException("ERROR: duplicate key value violates unique.");
		RuntimeException e1 = new RuntimeException(e2);
		PersistenceException e = new PersistenceException(e1);
		
		doThrow(e).when(conditionDAO).save(isA(Condition.class));
		
		Condition c = new Condition();
		c.setName("good");
		Response response = conditionService.create(c);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
	}

}
