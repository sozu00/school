package com.jiniguez.school.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jiniguez.school.model.Alumn;

@Repository
public interface AlumnDAO extends PagingAndSortingRepository<Alumn, Integer> {
	
	/**
	 * Obtiene los alumnos que nacieron ese dia
	 * @param day
	 * @return
	 */
	@Query(value = "select a from Alumn a"
			+ " where a.birthDate = :day")
	List<Alumn> findByDate(@Param(value = "day") Date day);
}
