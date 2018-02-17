package com.jiniguez.school.service;

import java.util.Date;
import java.util.List;

import com.jiniguez.school.dto.AlumnDTO;
import com.jiniguez.school.exception.NotFoundException;
import com.jiniguez.school.model.Alumn;

public interface AlumnService {

	/**
	 * Realiza la busqueda de todos los alumnos existentes
	 * @return Listado de alumnos
	 */
	List<AlumnDTO> findAll(Integer page, Integer size);
	
	/**
	 * Transforma un DTO en un alumno
	 * @param alumnDTO
	 * @return
	 */
	Alumn toModel(AlumnDTO alumnDTO);
	
	/**
	 * Transforma un alumno en DTO
	 * @param alumn
	 * @return
	 */
	AlumnDTO toDTO(Alumn alumn);
	
	/**
	 * Busca por ID
	 * @param id
	 * @return
	 */
	AlumnDTO findById(Integer id) throws NotFoundException;
	
	/**
	 * Busca todos los alumnos nacidos por en el dia
	 * @param day
	 * @return
	 */
	List<AlumnDTO> findByDate(Date day);
	
	/**
	 * Crea un alumno
	 * @param alumn
	 * @return
	 */
	AlumnDTO create(AlumnDTO alumnDTO);
	
	/**
	 * Modifica el alumno a partir del ID
	 * @param alumnID
	 * @param alumnDTO
	 */
	void update(Integer alumnID, AlumnDTO alumnDTO) throws NotFoundException;
	
	/**
	 * Elimina un alumno
	 * @param id
	 */
	void delete(Integer id) throws NotFoundException;
}
