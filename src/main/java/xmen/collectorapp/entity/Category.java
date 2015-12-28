package xmen.collectorapp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.Length;

import xmen.collectorapp.config.SettingsConfig;

@Entity
@NamedQueries(
		{ 
			@NamedQuery(name = "findAllCategories", query = "SELECT c FROM Category c"),
		})
@Table(
          name = "category", 
          schema = SettingsConfig.DATABASE_SCHEMA,
          uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})}
          )
@Check(constraints = "name ~'^[a-z ]+$'")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Length(min = 1, max = 50)
	private String name;

	
	public long getId()
	{
		return this.id;
	}
	
	public void setId(long id)
	{
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

}
