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
import org.hibernate.validator.constraints.NotEmpty;

import xmen.collectorapp.config.SettingsConfig;

@Entity
@NamedQueries(
		{
				@NamedQuery(name = "findAllKeywords", query = "SELECT k FROM Keyword k"),
		})
@Table(name = "keyword", schema = SettingsConfig.DATABASE_SCHEMA, uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Check(constraints = "name ~ '^[^A-Z]+$'")
public class Keyword implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@NotNull
	@Length(min = 1, max = 50)
	private String name;

	public void setId(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Keyword [id=" + id + ", name=" + name + "]";
	}

}
