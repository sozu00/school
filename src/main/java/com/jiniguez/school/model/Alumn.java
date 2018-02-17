package com.jiniguez.school.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Alumn implements Serializable{

	private static final long serialVersionUID = 5789637499580066934L;

	@Id
	@GeneratedValue
	@Column(name = "alumnID")
	private Integer id;
	
	private String alumnName;
	
	private String alumnEmail;
	
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	private String birthPlace;
	
}
