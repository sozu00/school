package com.jiniguez.school.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.jiniguez.school.config.Constants;
import com.jiniguez.school.dao.AlumnDAO;
import com.jiniguez.school.dto.AlumnDTO;
import com.jiniguez.school.exception.NotFoundException;
import com.jiniguez.school.model.Alumn;
import com.jiniguez.school.service.implementation.AlumnServiceImplementation;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestAlumnService {
	private static final Integer ID = 1;
	private static final String EMAIL = "email@gmail.com";
	private static final String NAME = "standardName";
	private static final Date BIRTHDATE = new Date(System.currentTimeMillis());
	private static final String BIRTHPLACE = "Cadiz";

	private AlumnDTO ALUMN_DTO = new AlumnDTO();
	private Alumn ALUMN = new Alumn();
	private List<Alumn> LIST_ALUMN;
	private Page<Alumn> ITERABLE_ALUMN;
	
	@InjectMocks
	AlumnService alumnService = new AlumnServiceImplementation();
	
	@Mock
	AlumnDAO alumnDAO;
	
	@Mock
	DozerBeanMapper dozer;
		
	@Before
	public void setUp() throws Exception {
		ALUMN_DTO.setAlumnEmail(EMAIL);
		ALUMN_DTO.setAlumnName(NAME);
		ALUMN_DTO.setBirthDate(BIRTHDATE);
		ALUMN_DTO.setBirthPlace(BIRTHPLACE);
		
		ALUMN.setId(ID);
		ALUMN.setAlumnEmail(EMAIL);
		ALUMN.setAlumnName(NAME);
		ALUMN.setBirthDate(BIRTHDATE);
		ALUMN.setBirthPlace(BIRTHPLACE);
		
		LIST_ALUMN = Arrays.asList(ALUMN);
		ITERABLE_ALUMN = new PageImpl<>(LIST_ALUMN);
		
		Mockito.when(dozer.map(ALUMN_DTO, Alumn.class)).thenReturn(ALUMN);
		Mockito.when(dozer.map(ALUMN, AlumnDTO.class)).thenReturn(ALUMN_DTO);
	}
	
//	toModel
	@Test
	public void testBaseToModel() {
		Alumn a = alumnService.toModel(ALUMN_DTO);
		assertEquals(ALUMN, a);
	}
	
//	toDTO
	@Test
	public void testBaseToDTO() {
		AlumnDTO a = alumnService.toDTO(ALUMN);
		assertEquals(ALUMN_DTO, a);
	}
	
//	findAll
	@Test
	public void testBaseFindAll() throws Exception {
		Mockito.when(alumnDAO.findAll(new PageRequest(Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE)))
			.thenReturn(ITERABLE_ALUMN);
		final List<AlumnDTO> findAll = alumnService.findAll(Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE);
		
		Assert.assertEquals(1, findAll.size());
		assertEquals(ALUMN_DTO,findAll.get(0));
	}
	@Test
	public void testNullParametersFindAll() throws Exception {
		Mockito.when(alumnDAO.findAll(new PageRequest(Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE)))
			.thenReturn(ITERABLE_ALUMN);
		final List<AlumnDTO> findAll = alumnService.findAll(null, null);

		Assert.assertEquals(1, findAll.size());
		assertEquals(ALUMN_DTO, findAll.get(0));
	}
	@Test
	public void testEmtpyFindAll() throws Exception {
		Mockito.when(alumnDAO.findAll(new PageRequest(Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE)))
				.thenReturn(new PageImpl<Alumn>(new ArrayList<>()));
		final List<AlumnDTO> findAll = alumnService.findAll(Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE);

		Assert.assertEquals(0, findAll.size());
	}
	
//	findById
	@Test
	public void testBaseFindById() throws Exception {
		Mockito.when(alumnDAO.findOne(ID)).thenReturn(ALUMN);
		AlumnDTO a = alumnService.findById(ID);
		
		assertEquals(ALUMN_DTO, a);
	}
	@Test(expected = NotFoundException.class)
	public void testNotFoundExceptionFindById() throws Exception {
		Mockito.when(alumnDAO.findOne(ID)).thenReturn(null);
		alumnService.findById(ID);
	}

//	findByDate
	@Test
	public void testBaseFindByDate() throws Exception {
		Mockito.when(alumnDAO.findByDate(BIRTHDATE)).thenReturn(Arrays.asList(ALUMN));
		
		final List<AlumnDTO> finByDate = alumnService.findByDate(BIRTHDATE);

		Assert.assertEquals(1, finByDate.size());
		assertEquals(ALUMN_DTO, finByDate.get(0));
	}
	@Test
	public void testEmtpyListFindByDate() throws Exception {
		Mockito.when(alumnDAO.findByDate(BIRTHDATE)).thenReturn(new ArrayList<>());
		
		final List<AlumnDTO> finByDate = alumnService.findByDate(BIRTHDATE);
		
		Assert.assertEquals(0, finByDate.size());
	}

//	create
	@Test
	public void testBaseCreate() throws Exception {
		Alumn ALUMN2 = new Alumn();
		ALUMN2.setAlumnEmail(EMAIL);
		ALUMN2.setAlumnName(NAME);
		ALUMN2.setBirthDate(BIRTHDATE);
		ALUMN2.setBirthPlace(BIRTHPLACE);
		
		Mockito.when(dozer.map(ALUMN_DTO, Alumn.class)).thenReturn(ALUMN2);
		Mockito.when(alumnDAO.save(ALUMN2)).thenReturn(ALUMN);
		
		AlumnDTO a = alumnService.create(ALUMN_DTO);
		
		assertEquals(ALUMN_DTO, a);
	}
	
//	update
	@Test
	public void testBaseUpdate() throws Exception {
		Mockito.when(alumnDAO.save(ALUMN)).thenReturn(ALUMN);
		Mockito.when(alumnDAO.exists(ID)).thenReturn(true);
		
		alumnService.update(ID, ALUMN_DTO);
		
	}
	@Test(expected = NotFoundException.class)
	public void testNotFoundExceptionUpdate() throws Exception {
		Mockito.when(alumnDAO.exists(ID)).thenReturn(false);
		alumnService.update(ID, ALUMN_DTO);
	}
	
	//delete
	@Test
	public void testBaseDelete() throws Exception {
		Mockito.when(alumnDAO.exists(ID)).thenReturn(true);
		Mockito.doNothing().when(alumnDAO).delete(ID);
		
		alumnService.delete(ID);
	}
	@Test(expected = NotFoundException.class)
	public void testNotFoundExceptionDelete() throws Exception {
		Mockito.when(alumnDAO.exists(ID)).thenReturn(false);
		
		alumnService.delete(ID);
	}

	
	private void assertEquals(AlumnDTO a, AlumnDTO b) {
		Assert.assertEquals(a.getAlumnEmail(), b.getAlumnEmail());
		Assert.assertEquals(a.getAlumnName(), b.getAlumnName());
		Assert.assertEquals(a.getBirthPlace(), b.getBirthPlace());
		Assert.assertEquals(a.getBirthDate(), b.getBirthDate());
	}
	private void assertEquals(Alumn a, Alumn b) {
		Assert.assertEquals(a.getAlumnEmail(), b.getAlumnEmail());
		Assert.assertEquals(a.getAlumnName(), b.getAlumnName());
		Assert.assertEquals(a.getBirthPlace(), b.getBirthPlace());
		Assert.assertEquals(a.getBirthDate(), b.getBirthDate());
	}
}
