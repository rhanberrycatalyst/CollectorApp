package xmen.collectorapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonFormat;

import xmen.collectorapp.config.SettingsConfig;

@Entity
@NamedQueries(
		{ 
			@NamedQuery(name = "findAllCollectibles", query = "SELECT c FROM Collectible c"), 
			@NamedQuery(name = "findCollectibleByID", query = "SELECT c FROM Collectible c WHERE c.id = :id"), 			
		})
@Table(name = "collectible", schema = SettingsConfig.DATABASE_SCHEMA, uniqueConstraints = { @UniqueConstraint(columnNames = { "catalogueNumber" }) })
@Check(constraints = "catalogueNumber ~'^[A-Z]{3}-[0-9]{12}$'")
// regex to pattern "AAA-111111111111"
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Collectible implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Length(max = 255)
	private String name;

	@Length(max = 1000)
	private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "00yyyy/MM/dd")
	private Date age;

	@NotNull
	private boolean isAD;

	@NotNull
	@Length(max = 16)
	private String catalogueNumber;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	// creates many to many join table for collectibles and colors
	@JoinTable(name = "hobby.color_collectible", joinColumns = { @JoinColumn(name = "collectible_id") }, inverseJoinColumns = { @JoinColumn(name = "color_id") })
	private Set<Color> colors = new HashSet<Color>();

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	// creates many to many join table for collectibles and keywords
	@JoinTable(name = "hobby.collectible_keyword", joinColumns = { @JoinColumn(name = "collectible_id") }, inverseJoinColumns = { @JoinColumn(name = "keyword_id") })
	private Set<Keyword> keywords = new HashSet<Keyword>();

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ownership_id")
	private Ownership ownership;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "condition_id")
	private Condition condition;

	public void addKeyword(Keyword keyword) {

		if (keywords == null) {
			keywords = new HashSet<Keyword>();

		}

		keywords.add(keyword);

	}

	public void addColor(Color color) {

		if (colors == null) {
			colors = new HashSet<Color>();

		}

		colors.add(color);

	}

	public boolean getIsAD() {
		return isAD;
	}

	public void setIsAD(boolean isAD) {
		this.isAD = isAD;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the age
	 */
	public Date getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(Date age) {
		this.age = age;
	}

	/**
	 * @return the catalogueNumber
	 */
	public String getCatalogueNumber() {
		return catalogueNumber;
	}

	/**
	 * @param catalogueNumber
	 *            the catalogueNumber to set
	 */
	public void setCatalogueNumber(String catalogueNumber) {
		this.catalogueNumber = catalogueNumber;
	}

	/**
	 * @return the colors
	 */
	public Set<Color> getColors() {
		return colors;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(Set<Color> colors) {
		this.colors = colors;
	}

	/**
	 * @return the Keywords
	 */
	public Set<Keyword> getKeywords() {
		return keywords;
	}

	/**
	 * @param Keywords
	 *            the Keywords to set
	 */
	public void setKeywords(Set<Keyword> Keywords) {
		this.keywords = Keywords;
	}

	/**
	 * @return the ownership
	 */
	public Ownership getOwnership() {
		return ownership;
	}

	/**
	 * @param ownership
	 *            the ownership to set
	 */
	public void setOwnership(Ownership ownership) {
		this.ownership = ownership;
	}

	/**
	 * @return the condition
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * @param condition
	 *            the condition to set
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

}
