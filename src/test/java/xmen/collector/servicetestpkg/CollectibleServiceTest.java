package xmen.collector.servicetestpkg;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import org.springframework.http.HttpStatus;

import xmen.collectorapp.dao.CategoryDAO;
import xmen.collectorapp.dao.CollectibleDAO;
import xmen.collectorapp.dao.ColorDAO;
import xmen.collectorapp.dao.ConditionDAO;
import xmen.collectorapp.dao.KeywordDAO;
import xmen.collectorapp.dao.OwnershipDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Category;
import xmen.collectorapp.entity.Collectible;
import xmen.collectorapp.entity.Condition;
import xmen.collectorapp.entity.Ownership;
import xmen.collectorapp.service.CollectibleService;


@RunWith(MockitoJUnitRunner.class)
public class CollectibleServiceTest {

	@Mock
	private CollectibleDAO collectibleDAO;
	
	@Mock
	private ConditionDAO conditionDAO;

	@Mock
	private OwnershipDAO ownershipDAO;

	@Mock
	private CategoryDAO categoryDAO;

	@Mock
	private ColorDAO colorDAO;

	@Mock
	private KeywordDAO keywordDAO;

	@InjectMocks
	private CollectibleService collectibleService;

	
	// Creates collectible with all fields containing valid data.
	// We then can set individual fields with invalid data to test.
	Collectible CreateValidTestCollectible()
	{
		Collectible collectible = new Collectible();
		collectible.setId(1l);
		collectible.setAge(new Date());
		collectible.setName("a pink vase called mike");
		collectible.setDescription("a good description");
		collectible.setCatalogueNumber("AAX-123123123123");
		
		Condition c = new Condition();
		c.setName("good");
		collectible.setCondition(c);
		
		Ownership o = new Ownership();
		o.setStatus("lost");
		collectible.setOwnership(o);
		
		Category cat = new Category();
		cat.setName("vase");
		collectible.setCategory(cat);
		
		return collectible;
	}
	
	
	@Before
	public void setUp() throws Exception {
		ArrayList<Collectible> collectibles = new ArrayList<Collectible>();

		collectibles.add(new Collectible());
		collectibles.add(new Collectible());
		collectibles.add(new Collectible());
		when(collectibleDAO.findAll()).thenReturn(collectibles); 

		// Needed for createValidCollectibleTest
		when(collectibleDAO.findCollectibleByID(anyLong()) ).thenReturn( CreateValidTestCollectible() ); 
		doNothing().when(collectibleDAO).save(isA(Collectible.class));
	
	}

	@Test
	public void findAllTest() {
		HttpStatus result = collectibleService.findAll().getStatusCode();
		assertEquals(result, HttpStatus.OK );

		@SuppressWarnings("unchecked")
		ArrayList<Collectible>  resultsCollectibles = (ArrayList<Collectible> ) collectibleService.findAll().getResponseObject();

		assertEquals(3, resultsCollectibles.size() );
	}
	
	@Test
	public void whenCreatingCollectibleWithNameTooLong() {
		Collectible collectible = CreateValidTestCollectible();
		// This name is 300 characters long. Card indicated max is 255
		collectible.setName("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
		
		HttpStatus result = collectibleService.create(collectible).getStatusCode();
		assertNotEquals(result, HttpStatus.CREATED );
	}

	@Test
	public void whenCreatingCollectibleWithDescriptionTooLong() {
		Collectible collectible = CreateValidTestCollectible();
		//1100 character description, too long
		collectible.setDescription("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
		
		HttpStatus result = collectibleService.create(collectible).getStatusCode();
		assertNotEquals(result, HttpStatus.CREATED );
	}
	
	
	@Test
	public void whenCreatingCollectibleWithInvalidCatalogFormat() {
		Collectible collectible = CreateValidTestCollectible();
		collectible.setCatalogueNumber("AAAX-12312312312");
		
		HttpStatus result = collectibleService.create(collectible).getStatusCode();
		assertNotEquals(result, HttpStatus.CREATED );
	}
	
	@Test
	public void whenCreatingValidCollectible() {
		Collectible collectible = CreateValidTestCollectible();
		
		xmen.collectorapp.dto.Response response = collectibleService.create(collectible);
		assertEquals(HttpStatus.CREATED, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingWithNonUniqueCatalogNumber() {
		// Created dummy exception to be throw on collectibleDAO.save
		// mainly to increase code test coverage
		RuntimeException e2 = new RuntimeException("ERROR: duplicate key value violates unique.");
		RuntimeException e1 = new RuntimeException(e2);
		PersistenceException e = new PersistenceException(e1);
		
		doThrow(e).when(collectibleDAO).save(isA(Collectible.class));
		
		Collectible collectible = CreateValidTestCollectible();
		Response response = collectibleService.create(collectible);


		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
	}
	
}

