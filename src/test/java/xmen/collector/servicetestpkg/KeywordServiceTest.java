package xmen.collector.servicetestpkg;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
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

//import org.mockito.Matchers;
import xmen.collectorapp.dao.KeywordDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Keyword;
import xmen.collectorapp.service.KeywordService;
import xmen.collectorapp.util.UpdatingNonExistantEntityException;

@RunWith(MockitoJUnitRunner.class)
public class KeywordServiceTest {

	@Mock
	private KeywordDAO keywordDAO;
	
	@InjectMocks
	private KeywordService keywordService;

	@Before
	public void setUp() throws Exception {
		ArrayList<Keyword> keywords = new ArrayList<Keyword>();

		Keyword k = new Keyword();
		k.setName("dupe");
		keywords.add(k);
		
		keywords.add(new Keyword());
		keywords.add(new Keyword());
		keywords.add(new Keyword());
		
		when(keywordDAO.findAll()).thenReturn(keywords);
	}

	@Test
	public void whenFindAll() {
		@SuppressWarnings("unchecked")
		ArrayList<Keyword> keywords = (ArrayList<Keyword>) keywordService
				.findAll().getResponseObject();

		assertEquals(4, keywords.size());
	}

	@Test
	public void ShouldPassCode() {
		HttpStatus result = keywordService.findAll().getStatusCode();
		assertEquals(result, HttpStatus.OK);
	}

	@Test
	public void ShouldPassAssertFalseHttpCode() {
		HttpStatus result = keywordService.findAll().getStatusCode();
		assertFalse(result.equals(HttpStatus.NOT_FOUND));
	}
	
	@Test
	public void whenCreatingValidKeyword() {
		Keyword k = new Keyword();
		k.setName("phased");
		Response response = keywordService.create(k);

		assertEquals(HttpStatus.CREATED, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingKeywordTooLong() {
		Keyword k = new Keyword();
		k.setName("petrified dog 0123456789001234567890012345678900123456789001234567890");
		Response response = keywordService.create(k);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingKeywordEmpty() {
		Keyword k = new Keyword();
		k.setName("");
		Response response = keywordService.create(k);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingKeywordWithUpperCase() {
		Keyword k = new Keyword();
		k.setName("DOG");
		Response response = keywordService.create(k);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenUpdatingNonExistantKeyword() {
		
		UpdatingNonExistantEntityException e = new UpdatingNonExistantEntityException();
		
		doThrow(e).when(keywordDAO).update(isA(Keyword.class));

		
		Keyword k = new Keyword();
		k.setId(123456l);
		k.setName("dog");
		Response response = keywordService.update(k);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
	}
	
	
	@Test
	public void whenUpdatingValidKeyword() {
		Keyword k = new Keyword();
		k.setId(1);
		k.setName("dog");
		Response response = keywordService.update(k);

		assertEquals(HttpStatus.CREATED, response.getStatusCode() );
	}
	
	@Test
	public void whenUpdatingKeywordWithLengthToLong() {
		Keyword k = new Keyword();
		k.setId(1);
		k.setName("dog012345678901234567890123456789012345678901234567890123456789");
		Response response = keywordService.update(k);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	
	@Test
	public void whenCreatingKeywordDuplicate() {
		// Created dummy exception to be throw on KeywordDAO.save
		// mainly to increase code test coverage
		RuntimeException e2 = new RuntimeException("ERROR: duplicate key value violates unique.");
		RuntimeException e1 = new RuntimeException(e2);
		PersistenceException e = new PersistenceException(e1);
		
		doThrow(e).when(keywordDAO).save(isA(Keyword.class));
		
		Keyword k = new Keyword();
		k.setName("dupe");
		Response response = keywordService.create(k);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
	}
	
}
