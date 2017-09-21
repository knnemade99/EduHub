package com.eduhub.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="standard")
public class Standard {
	
	@Id
	@SequenceGenerator(name = "standardId", sequenceName = "standard_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "standardId")
	@Column(name="standardId")
	private int standardId;
	
	public Standard() {
		super();
	}

	public Standard(String standardName) {
		super();
		this.standardName = standardName;
	}

	@Override
	public String toString() {
		return "Standard [standardId=" + standardId + ", standardName=" + standardName + "]";
	}

	public int getStandardId() {
		return standardId;
	}

	public void setStandardId(int standardId) {
		this.standardId = standardId;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	@Column(name="standardName")
	private String standardName;
}
