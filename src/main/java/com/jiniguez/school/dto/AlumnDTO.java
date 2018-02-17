package com.jiniguez.school.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AlumnDTO {

	private String alumnName;
	private String alumnEmail;
	private Date birthDate;
	private String birthPlace;
	
	public String toString() {
		return alumnName;
	}
}
