package com.jiniguez.school.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jiniguez.school.dto.AlumnDTO;
import com.jiniguez.school.exception.NotFoundException;
import com.jiniguez.school.service.AlumnService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/alumn")
public class AlumnController {

	@Autowired
	private AlumnService alumnService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public List<AlumnDTO> findAll(@RequestParam(required=false) Integer page,
			@RequestParam(required = false) Integer size){
		log.info(String.format("Buscando todos los alumnos de la pagina %d de tamaño %d",page,size));
		return alumnService.findAll(page, size);
	}
	
	@RequestMapping(method = {RequestMethod.GET}, value = "/{alumnID}")
	public AlumnDTO findById(@PathVariable Integer alumnID) throws NotFoundException {
		return alumnService.findById(alumnID);
	}
	
	@RequestMapping(method = {RequestMethod.GET}, value = "/date/{day}")
	public List<AlumnDTO> findByDate(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date day){
		return alumnService.findByDate(day);
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public AlumnDTO create(@RequestBody AlumnDTO alumnDTO) {
		return alumnService.create(alumnDTO);
	}
	
	@RequestMapping(method = {RequestMethod.PUT}, value = "/{alumnID}")
	public void update(@PathVariable Integer alumnID, @RequestBody AlumnDTO alumnDTO) throws NotFoundException {
		alumnService.update(alumnID, alumnDTO);
	}
	
	@RequestMapping(method = {RequestMethod.DELETE}, value = "/{alumnID}")
	public void delete(@PathVariable Integer alumnID) throws NotFoundException {
		alumnService.delete(alumnID);
	}
}
