package xmen.collectorapp.webservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xmen.collectorapp.dao.CollectibleDAO;
import xmen.collectorapp.entity.Collectible;

/**
 * Rest controller
 */
@RestController
public class CollectibleWebService {

	@Autowired
	private CollectibleDAO collectibleService;

	public void setCollectibleService(CollectibleDAO collectibleService) {
		this.collectibleService = collectibleService;
	}

	/**
	 * POST
	 */
	@RequestMapping(value = "/collectibles", method = RequestMethod.POST)
	public void addTask(@RequestBody Collectible collectible) {
		System.out.println("WebServPOST");
		collectibleService.addCollectible(collectible);
	}

	/**
	 * GET
	 */
	@RequestMapping(value = "/collectibles", method = RequestMethod.GET)
	public List<Collectible> getCollectibles() {
		return collectibleService.getAllCollectibles();
	}

	/**
	 * PUT
	 */
	@RequestMapping(value = "/collectibles", method = RequestMethod.PUT)
	public void updateCollectible(@RequestBody Collectible collectible) {
		collectibleService.updateCollectible(collectible);
	}
}
