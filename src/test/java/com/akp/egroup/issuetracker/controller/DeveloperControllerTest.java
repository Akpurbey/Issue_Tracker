package com.akp.egroup.issuetracker.controller;

import com.akp.egroup.issuetracker.model.Developer;
import com.akp.egroup.issuetracker.service.DeveloperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DeveloperControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeveloperService service;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAll() throws Exception {
        String url = "/developer";
        mockMvc.perform(get(url)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void createDeveloper() throws Exception {
        Developer dev= new Developer();
        dev.setId(1);
        dev.setName("Arbind");
        String url = "/developer";
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dev))).andExpect(status().isOk())
                .andDo(print());

    }


}
