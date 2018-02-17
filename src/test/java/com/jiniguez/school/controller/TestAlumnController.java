package com.jiniguez.school.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jiniguez.school.config.Constants;
import com.jiniguez.school.dto.AlumnDTO;
import com.jiniguez.school.exception.NotFoundException;
import com.jiniguez.school.service.AlumnService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestAlumnController {

	private static final Integer ID = 1;
	
	private static final String EMAIL = "email@gmail.com";

	private static final String NAME = "standardName";

	private static final Date BIRTHDATE = new Date(System.currentTimeMillis());

	private static final String BIRTHPLACE = "Cadiz";

	private AlumnDTO alumnDTO = new AlumnDTO();
	
	@InjectMocks
	private AlumnController alumnController = new AlumnController();
	
	@Mock
	private AlumnService alumnService;
	
	MockMvc mockMvc;
	@Before
	public void setUp() {
		alumnDTO.setAlumnEmail(EMAIL);
		alumnDTO.setAlumnName(NAME);
		alumnDTO.setBirthDate(BIRTHDATE);
		alumnDTO.setBirthPlace(BIRTHPLACE);
		
		mockMvc = MockMvcBuilders
				.standaloneSetup(alumnController)
		        .alwaysExpect(status().isOk())
		        .build();
	}
	
	//findAll
	@Test
	public void testBaseFindAll() throws Exception {
		Mockito.when(alumnService.findAll(Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE)).thenReturn(Arrays.asList(alumnDTO));
		final List<AlumnDTO> findAll = alumnController.findAll(Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE);
		
		mockMvc.perform(get("/alumn"));

		Assert.assertEquals(findAll.size(), 1);
		assertEquals(findAll.get(0), alumnDTO);
	}
	@Test
	public void testNullParametersFindAll() throws Exception {
		Mockito.when(alumnService.findAll(null, null)).thenReturn(Arrays.asList(alumnDTO));
		final List<AlumnDTO> findAll = alumnController.findAll(null, null);

		mockMvc.perform(get("/alumn"));

		Assert.assertEquals(findAll.size(), 1);
		assertEquals(findAll.get(0), alumnDTO);
	}
	
	@Test
	public void testEmtpyFindAll() throws Exception {
		Mockito.when(alumnService.findAll(Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE)).thenReturn(new ArrayList<>());
		final List<AlumnDTO> findAll = alumnController.findAll(Constants.DEFAULT_PAGE, Constants.DEFAULT_SIZE);
		
		mockMvc.perform(get("/alumn"));

		Assert.assertEquals(findAll.size(), 0);
	}
	

	//findById
	@Test
	public void testBaseFindById() throws Exception {
		Mockito.when(alumnService.findById(ID)).thenReturn(alumnDTO);
		
		mockMvc.perform(get("/alumn/"+ID));
		
		AlumnDTO a = alumnController.findById(ID);
		assertEquals(a, alumnDTO);
	}
	@Test(expected = NotFoundException.class)
	public void testNotFoundExceptionFindById() throws NotFoundException{
		Mockito.when(alumnService.findById(ID)).thenThrow(new NotFoundException());
		alumnController.findById(ID);
	}
	
	//findByDate
	@Test
	public void testBaseFindByDate() throws Exception {
		Mockito.when(alumnService.findByDate(BIRTHDATE)).thenReturn(Arrays.asList(alumnDTO));
		
		final List<AlumnDTO> finByDate = alumnController.findByDate(BIRTHDATE);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
		mockMvc.perform(get("/alumn/date/"+sdf.format(BIRTHDATE)));

		Assert.assertEquals(finByDate.size(), 1);
		assertEquals(finByDate.get(0), alumnDTO);
	}
	@Test
	public void testEmtpyListFindByDate() throws Exception {
		Mockito.when(alumnService.findByDate(BIRTHDATE)).thenReturn(new ArrayList<>());
		
		final List<AlumnDTO> finByDate = alumnController.findByDate(BIRTHDATE);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
		mockMvc.perform(get("/alumn/date/"+sdf.format(BIRTHDATE)));

		Assert.assertEquals(finByDate.size(), 0);
	}
	
	//create
	@Test
	public void testBaseCreate() throws Exception {
		Mockito.when(alumnService.create(alumnDTO)).thenReturn(alumnDTO);
		
		AlumnDTO a = alumnController.create(alumnDTO);
		
		mockMvc.perform(post("/alumn")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createJson(NAME, EMAIL, BIRTHDATE, BIRTHPLACE))
				);
		
		assertEquals(a, alumnDTO);
	}
	
	//update
	@Test
	public void testBaseUpdate() throws Exception {
		Mockito.doNothing().when(alumnService).update(ID,alumnDTO);
		
		alumnController.update(ID, alumnDTO);
		
		mockMvc.perform(put("/alumn/"+ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(createJson(NAME, EMAIL, BIRTHDATE, BIRTHPLACE))
				);
	}
	
	@Test(expected = NotFoundException.class)
	public void testNotFoundExceptionUpdate() throws Exception {
		Mockito.doThrow(NotFoundException.class).when(alumnService).update(ID,alumnDTO);
		
		alumnController.update(ID, alumnDTO);
		
		mockMvc.perform(put("/alumn/"+ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(createJson(NAME, EMAIL, BIRTHDATE, BIRTHPLACE))
				);
	}
	
	//delete
	@Test
	public void testBaseDelete() throws Exception {
		Mockito.doNothing().when(alumnService).delete(ID);
		
		alumnController.delete(ID);
		
		mockMvc.perform(delete("/alumn/"+ID));
	}
	
	@Test(expected = NotFoundException.class)
	public void testNotFoundExceptionDelete() throws Exception {
		Mockito.doThrow(NotFoundException.class).when(alumnService).delete(ID);
		alumnController.delete(ID);
		mockMvc.perform(delete("/alumn/"+ID));
	}
	
	private void assertEquals(AlumnDTO a, AlumnDTO b) {
		Assert.assertEquals(a.getAlumnEmail(), b.getAlumnEmail());
		Assert.assertEquals(a.getAlumnName(), b.getAlumnName());
		Assert.assertEquals(a.getBirthPlace(), b.getBirthPlace());
		Assert.assertEquals(a.getBirthDate(), b.getBirthDate());
	}
	
	private static String createJson (String alumnName, String alumnEmail, Date birthDate, String birthPlace) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
        return "{\"alumnName\": \"" + alumnName + "\", " +
                            "\"alumnEmail\": \"" + alumnEmail + "\"," +
                            "\"birthDate\": \"" + sdf.format(birthDate) + "\", " +
                            "\"birthPlace\": \"" + birthPlace + "\"}";
    }
}
