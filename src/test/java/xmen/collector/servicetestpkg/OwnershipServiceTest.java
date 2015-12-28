package xmen.collector.servicetestpkg;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import xmen.collectorapp.dao.OwnershipDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Ownership;
import xmen.collectorapp.service.OwnershipService;


@RunWith(MockitoJUnitRunner.class)
public class OwnershipServiceTest {

	@Mock
	private OwnershipDAO ownershipDAO;
	@InjectMocks
	private OwnershipService ownershipService;
	
	@Before
	public void setUp() throws Exception {
		ArrayList<Ownership> ownerships = new ArrayList<Ownership>();

		ownerships.add(new Ownership());
		ownerships.add(new Ownership());
		ownerships.add(new Ownership());
		ownerships.add(new Ownership());
		when(ownershipDAO.findAll()).thenReturn(ownerships); 
	}

	@Test
	public void whenFindALl() {
		HttpStatus result = ownershipService.findAll().getStatusCode();
		assertEquals(result,HttpStatus.OK );
		@SuppressWarnings("unchecked")
		ArrayList<Ownership>  ownerships = (ArrayList<Ownership> ) ownershipService.findAll().getResponseObject();

		assertEquals(4, ownerships.size() );
		
	}
	
	@Test
	public void whenCreatingWithStatusTooLong() {
		Ownership ownership = new Ownership();
		ownership.setStatus("jacked 0123456789001234567890012345678900123456789001234567890");
		Response response = ownershipService.create(ownership);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingWithStatusTooShort() {
		Ownership ownership = new Ownership();
		ownership.setStatus("");
		Response response = ownershipService.create(ownership);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingWithDuplicateStatus() {
		// Created dummy exception to be throw on KeywordDAO.save
		// mainly to increase code test coverage
		RuntimeException e2 = new RuntimeException("ERROR: duplicate key value violates unique.");
		RuntimeException e1 = new RuntimeException(e2);
		PersistenceException e = new PersistenceException(e1);
		
		doThrow(e).when(ownershipDAO).save(isA(Ownership.class));
		
		Ownership ownership = new Ownership();
		ownership.setStatus("dupe");
		Response response = ownershipService.create(ownership);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
	}
	

}
