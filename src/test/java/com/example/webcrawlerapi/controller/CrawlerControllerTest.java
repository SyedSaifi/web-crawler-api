package com.example.webcrawlerapi.controller;

import com.example.webcrawlerapi.model.PageInformation;
import com.example.webcrawlerapi.service.ICrawlerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CrawlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICrawlerService crawlerService;

    private PageInformation pageInfomation;

    @Before
    public void setUp() throws Exception {
        pageInfomation = new PageInformation("someurl");
    }

    @Test
    public void testGetPageInformation() throws Exception{
        when(crawlerService.crawl(anyString(), anyInt(), any(PageInformation.class), anyList()))
                .thenReturn(pageInfomation);

        MvcResult mvcResult = mockMvc.perform(get("/crawler?url=someurl&depth=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}