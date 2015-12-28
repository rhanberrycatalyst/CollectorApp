package xmen.collector.servicetestpkg;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import javax.persistence.PersistenceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import xmen.collectorapp.dao.ColorDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Color;
import xmen.collectorapp.service.ColorService;


@RunWith(MockitoJUnitRunner.class)
public class ColorServiceTest {

	@Mock
	private ColorDAO colorDAO;
	@InjectMocks
	private ColorService colorService;
	
	@Before
	public void setUp() throws Exception {
		ArrayList<Color> colors = new ArrayList<Color>();

		colors.add(new Color());
		colors.add(new Color());
		colors.add(new Color());
		when(colorDAO.findAll()).thenReturn(colors); 
	}

	@Test
	public void findAllTest() {
		HttpStatus result = colorService.findAll().getStatusCode();
		assertEquals(result,HttpStatus.OK );
		@SuppressWarnings("unchecked")
		ArrayList<Color>  colors = (ArrayList<Color> ) colorService.findAll().getResponseObject();

		assertEquals(3, colors.size() );
		
	}

	@Test
	public void ShouldPassService() {
		@SuppressWarnings("unchecked")
		ArrayList<Color> keywords = (ArrayList<Color>) colorService
				.findAll().getResponseObject();

		assertEquals(3, keywords.size());
	}

	@Test
	public void ShouldPassCode() {
		HttpStatus result = colorService.findAll().getStatusCode();
		assertEquals(result, HttpStatus.OK);
	}

	@Test
	public void whenCreatingColorTooLong() {
		Color k = new Color();
		k.setName("petrified dog aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		Response response = colorService.create(k);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingKeywordEmpty() {
		Color k = new Color();
		k.setName("");
		Response response = colorService.create(k);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	@Test
	public void whenCreatingKeywordWithUpperCase() {
		Color k = new Color();
		k.setName("DOG");
		Response response = colorService.create(k);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode() );
	}
	
	
	@Test
	public void successfulPost() {
		Color k = new Color();
		k.setName("stuff");
		Response response = colorService.create(k);

		assertEquals(HttpStatus.CREATED, response.getStatusCode() );
	}
	
	@Test
	public void createDuplicateValues() {
		
		RuntimeException e1 = new RuntimeException("ERROR: duplicate key value violates unique.");
		RuntimeException e2 = new RuntimeException(e1);
		PersistenceException e = new PersistenceException(e2); 
		
		doThrow(e).when(colorDAO).save(org.mockito.Matchers.isA(Color.class));
		
		Color k = new Color();
		k.setName("stuff");
		
		Response response = colorService.create(k);
	
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
	}
	
}
