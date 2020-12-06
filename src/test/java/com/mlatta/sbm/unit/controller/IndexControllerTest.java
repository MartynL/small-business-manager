package com.mlatta.sbm.unit.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
class IndexControllerTest {

	@Autowired private MockMvc mockMvc;

	@Test
	void rootPathShouldReturnIndexView() throws Exception {

		mockMvc
			.perform(MockMvcRequestBuilders.get("/"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("Index"));
			
		
	}
	
}
