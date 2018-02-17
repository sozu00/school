package com.jiniguez.school.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jiniguez.school.config.Constants;
import com.jiniguez.school.dao.AlumnDAO;
import com.jiniguez.school.dto.AlumnDTO;
import com.jiniguez.school.exception.NotFoundException;
import com.jiniguez.school.model.Alumn;
import com.jiniguez.school.service.AlumnService;

@Service
public class AlumnServiceImplementation implements AlumnService{

	@Autowired
	private AlumnDAO alumnDAO;
	
	@Autowired
	private DozerBeanMapper dozer;

	@Override
	public Alumn toModel(AlumnDTO alumnDTO) {
		return dozer.map(alumnDTO, Alumn.class);
	}

	@Override
	public AlumnDTO toDTO(Alumn alumn) {
		return dozer.map(alumn, AlumnDTO.class);
	}
	
	@Override
	public List<AlumnDTO> findAll(Integer page, Integer size) {
		Integer p = Optional.ofNullable(page).orElse(Constants.DEFAULT_PAGE);
		Integer s = Optional.ofNullable(size).orElse(Constants.DEFAULT_SIZE);
		final Iterable<Alumn> findAll = alumnDAO.findAll(new PageRequest(p, s));
		final List<AlumnDTO> finalList = new ArrayList<>();
		findAll.forEach(a->finalList.add(toDTO(a)));
		
		return finalList;
	}

	@Override
	public AlumnDTO findById(Integer id) throws NotFoundException {
		final Alumn a = Optional.ofNullable(alumnDAO.findOne(id)).orElseThrow(NotFoundException::new);
		return toDTO(a);
	}

	@Override
	public List<AlumnDTO> findByDate(Date day) {
		final Iterable<Alumn> findByDate = alumnDAO.findByDate(day);
		final List<AlumnDTO> finalList = new ArrayList<>();
		findByDate.forEach(a->finalList.add(toDTO(a)));
		
		return finalList;
	}

	@Override
	public AlumnDTO create(AlumnDTO alumnDTO) {
		final Alumn result = alumnDAO.save(toModel(alumnDTO));
		return toDTO(result);
	}

	@Override
	public void update(Integer alumnID, AlumnDTO alumnDTO) throws NotFoundException {
		final Alumn alumn = toModel(alumnDTO);
		alumn.setId(alumnID);
		if(alumnDAO.exists(alumnID))
			alumnDAO.save(alumn);
		else
			throw new NotFoundException();
	}

	@Override
	public void delete(Integer id) throws NotFoundException {
		if (alumnDAO.exists(id))
			alumnDAO.delete(id);
		else
			throw new NotFoundException();
	}

}
