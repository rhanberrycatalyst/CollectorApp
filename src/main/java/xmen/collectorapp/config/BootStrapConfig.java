package xmen.collectorapp.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xmen.collectorapp.entity.Category;
import xmen.collectorapp.entity.Collectible;
import xmen.collectorapp.entity.Color;
import xmen.collectorapp.entity.Condition;
import xmen.collectorapp.entity.Keyword;
import xmen.collectorapp.entity.Ownership;

@Component
public class BootStrapConfig {

	@Autowired
	BootstrapDAO bootStrapData;

	@PostConstruct
	public void populateData() throws ParseException {
		List<Color> colors = populateColors();
		List<Category> categories = populateCategories();
		List<Condition> conditions = populateConditions();
		List<Keyword> keywords = populateKeywords();
		List<Ownership> ownerships = populateOwnerships();
		populateCollectibles(colors,
				categories, keywords, conditions, ownerships);
	}

	public List<Color> populateColors() {

		List<Color> colors = new ArrayList<Color>();
		
		Color purple = new Color();
		purple.setName("purple");
		bootStrapData.save(purple);
		colors.add(purple);
		
 		Color green = new Color();
		green.setName("brown");
		bootStrapData.save(green);
		colors.add(green);

		Color red = new Color();
		red.setName("blue");
		bootStrapData.save(red);
		colors.add(red);
		
		Color black = new Color();
		black.setName("yellow");
		bootStrapData.save(black);
		colors.add(black);
		
		Color white = new Color();
		white.setName("white");
		bootStrapData.save(white);
		colors.add(white);
		

		
		return colors;
	}

	public List<Category> populateCategories() {
		List<Category> categories = new ArrayList<Category>();

		Category powerup = new Category();
		powerup.setName("powerup");
		bootStrapData.save(powerup);
		categories.add(powerup);

		Category weapon = new Category();
		weapon.setName("weapon");
		bootStrapData.save(weapon);
		categories.add(weapon);
		
		Category vehicle = new Category();
		vehicle.setName("vehicle");
		bootStrapData.save(vehicle);
		categories.add(vehicle);
		
		Category treasure = new Category();
		treasure.setName("treasure");
		bootStrapData.save(treasure);
		categories.add(treasure);
		
		return categories;
	}

	public List<Condition> populateConditions() {
		List<Condition> conditions = new ArrayList<Condition>();

		Condition mint = new Condition();
		mint.setName("mint");
		bootStrapData.save(mint);
		conditions.add(mint);
		
		Condition slightlyUsed = new Condition();
		slightlyUsed.setName("slightly used");
		bootStrapData.save(slightlyUsed);
		conditions.add(slightlyUsed);

		Condition poor = new Condition();
		poor.setName("poor");
		bootStrapData.save(poor);
		conditions.add(poor);
		
		Condition terrible = new Condition();
		terrible.setName("terrible");
		bootStrapData.save(terrible);
		conditions.add(terrible);
		
		return conditions;
	}

	public List<Keyword> populateKeywords() {
		List<Keyword> keywords = new ArrayList<Keyword>();

		Keyword roman = new Keyword();
		roman.setName("roman");
		bootStrapData.save(roman);
		keywords.add(roman);

		Keyword imperial = new Keyword();
		imperial.setName("imperial");
		bootStrapData.save(imperial);
		keywords.add(imperial);
		
		Keyword jewelry = new Keyword();
		jewelry.setName("jewelry");
		bootStrapData.save(jewelry);
		keywords.add(jewelry);
		
		Keyword ancient = new Keyword();
		ancient.setName("ancient");
		bootStrapData.save(ancient);
		keywords.add(ancient);
		
		
		return keywords;
	}

	public List<Ownership> populateOwnerships() {
		List<Ownership> ownerships = new ArrayList<Ownership>();

		Ownership purchased = new Ownership();
		purchased.setStatus("purchased");
		purchased.setOwnedByMe(true);
		bootStrapData.save(purchased);
		ownerships.add(purchased);

		Ownership found = new Ownership();
		found.setStatus("found");
		found.setOwnedByMe(true);
		bootStrapData.save(found);
		ownerships.add(found);
		
		Ownership destroyed = new Ownership();
		destroyed.setStatus("destroyed");
		destroyed.setOwnedByMe(false);
		bootStrapData.save(destroyed);
		ownerships.add(destroyed);
		
		Ownership lost = new Ownership();
		lost.setStatus("lost");
		lost.setOwnedByMe(false);
		bootStrapData.save(lost);
		ownerships.add(lost);
		
		return ownerships;
	}

	public void populateCollectibles(List<Color> colors,
			List<Category> categories, List<Keyword> keywords,
			List<Condition> conditions, List<Ownership> ownerships)
			throws ParseException {
		

		Collectible battleship = new Collectible();
		battleship.addKeyword(keywords.get(0));
		battleship.addColor(colors.get(0));
		battleship.addColor(colors.get(1));
		battleship.setAge(dateFromString("03/10/2010"));
		battleship.setCatalogueNumber("XXX-123451234512");
		battleship.setCategory(categories.get(2));
		battleship.setCondition(conditions.get(1));
		battleship.setDescription("caldari navy class battleship");
		battleship.setIsAD(true);
		battleship.setName("Raven");
		battleship.setOwnership(ownerships.get(1));
		bootStrapData.save(battleship);
		
		Collectible lightSaber = new Collectible();
		lightSaber.addKeyword(keywords.get(1));
		lightSaber.addColor(colors.get(2));
		lightSaber.setAge(dateFromString("03/10/2010"));
		lightSaber.setCatalogueNumber("XXX-001122334455");
		lightSaber.setCategory(categories.get(1));
		lightSaber.setCondition(conditions.get(0));
		lightSaber.setDescription("be careful with that");
		lightSaber.setIsAD(true);
		lightSaber.setName("sith light saber");
		lightSaber.setOwnership(ownerships.get(0));
		bootStrapData.save(lightSaber);
		
		Collectible blackbird = new Collectible();
		blackbird.addKeyword(keywords.get(0));
		blackbird.addColor(colors.get(3));
		blackbird.setAge(dateFromString("03/10/2010"));
		blackbird.setCatalogueNumber("XXX-123451234519");
		blackbird.setCategory(categories.get(0));
		blackbird.setCondition(conditions.get(3));
		blackbird.setDescription("SR-71 Blackbird");
		blackbird.setIsAD(true);
		blackbird.setName("sr71");
		blackbird.setOwnership(ownerships.get(2));
		bootStrapData.save(blackbird);

		
	}

	private Date dateFromString(String dateInString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date parsedDate = formatter.parse(dateInString);
		return parsedDate;
	}

}
