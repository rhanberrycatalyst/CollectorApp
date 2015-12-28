package xmen.collector.servicetestpkg;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import xmen.collectorapp.dao.CategoryDAO;
import xmen.collectorapp.entity.Category;
import xmen.collectorapp.service.CategoryService;


@RunWith(MockitoJUnitRunner.class)
public class CategoryGetTest {

	@Mock
	private CategoryDAO categoryDAO;
	@InjectMocks
	private CategoryService categoryService;
	
	@Before
	public void setUp() throws Exception {
		List<Category> categories = new ArrayList<Category>();

		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		when(categoryDAO.findAllCategory()).thenReturn(categories); 
	}


	
	@Test
	public void ShouldGet10() {
		
		@SuppressWarnings("unchecked")
		ArrayList<Category>  resultsCategories = (ArrayList<Category> ) categoryService.findAllCategory().getResponseObject();
		assertEquals(10, resultsCategories.size() );
		
	}
	
}
