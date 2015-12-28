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
import org.hibernate.validator.constraints.Length;

import xmen.collectorapp.config.SettingsConfig;

@Entity
@NamedQueries(
				{ 
					@NamedQuery(name = "findAllOwnerships", query = "SELECT r FROM Ownership r")
				})
@Table(
          name = "ownership", 
          schema = SettingsConfig.DATABASE_SCHEMA,
          uniqueConstraints = {@UniqueConstraint(columnNames = {"status"})}
          )
//@Check(constraints = "status ~'^[a-z ]+$'") //only accepts strings of lower case letters and spaces.// Original requirement from HN-37 that was removed
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Ownership implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Length(min = 1, max = 50)
	private String status;

	@NotNull
	private boolean ownedByMe;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the ownedByMe
	 */
	public boolean isOwnedByMe() {
		return ownedByMe;
	}

	/**
	 * @param ownedByMe
	 *            the ownedByMe to set
	 */
	public void setOwnedByMe(boolean ownedByMe) {
		this.ownedByMe = ownedByMe;
	}

}
