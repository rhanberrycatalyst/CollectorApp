package xmen.collector.servicetestpkg;

import static org.junit.Assert.assertEquals;
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

import xmen.collectorapp.dao.CategoryDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Category;
import xmen.collectorapp.service.CategoryService;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

	@Mock
	private CategoryDAO categoryDAO;
	@InjectMocks
	private CategoryService categoryService;

	@Before
	public void setUp() throws Exception {
		ArrayList<Category> categories = new ArrayList<Category>();

		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		when(categoryDAO.findAllCategory()).thenReturn(categories);
	}

	@Test
	public void ShouldPassService() {
		@SuppressWarnings("unchecked")
		ArrayList<Category> categories = (ArrayList<Category>) categoryService
				.findAllCategory().getResponseObject();

		assertEquals(3, categories.size());
	}

	@Test
	public void ShouldPassCode() {
		HttpStatus result = categoryService.findAllCategory().getStatusCode();
		assertEquals(result, HttpStatus.OK);
	}

	@Test
	public void whenCreatingCategoryTooLong() {
		Category k = new Category();
		k.setName("petrified dog aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		Response response = categoryService.create(k);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingCategoryEmpty() {
		Category k = new Category();
		k.setName("");
		Response response = categoryService.create(k);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingCategoryWithUpperCase() {
		Category k = new Category();
		k.setName("DOG");
		Response response = categoryService.create(k);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	
	@Test
	public void successfulPost() {
		Category k = new Category();
		k.setName("stuff");
		Response response = categoryService.create(k);

		assertEquals(HttpStatus.CREATED, response.getStatusCode() );
	}
	
	@Test
	public void createDuplicateValues() {
		
		RuntimeException e1 = new RuntimeException("ERROR: duplicate key value violates unique.");
		RuntimeException e2 = new RuntimeException(e1);
		PersistenceException e = new PersistenceException(e2); 
		
		doThrow(e).when(categoryDAO).save(org.mockito.Matchers.isA(Category.class));
		
		Category k = new Category();
		k.setName("stuff");
		
		Response response = categoryService.create(k);
	
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
	}
	
}
