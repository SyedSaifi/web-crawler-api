package com.example.webcrawlerapi.controller;

import com.example.webcrawlerapi.configuration.AppPropertiesConfig;
import com.example.webcrawlerapi.model.PageInformation;
import com.example.webcrawlerapi.service.ICrawlerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CrawlerController.class)
public class CrawlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICrawlerService crawlerService;

    @MockBean
    private AppPropertiesConfig crawlerProperties;

    private PageInformation pageInfomation;

    @Before
    public void setUp() throws Exception {
        pageInfomation = new PageInformation("http://someurl");
        pageInfomation.setExternalLinks(0);
        pageInfomation.setInternalLinks(1);
    }

    @Test
    public void testGetPageInformationIncorrectUrl() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/crawler").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        Assert.assertEquals("Required String parameter 'url' is not present",
                mvcResult.getResponse().getErrorMessage());
    }

    @Test
    public void testGetPageInformation() throws Exception{
        when(crawlerService.crawl(anyString(), anyInt(), eq(true)))
                .thenReturn(pageInfomation);

        mockMvc.perform(get("/crawler?url=someurl&depth=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseUrl").value("http://someurl"))
                .andExpect(jsonPath("$.internalLinks").value(1))
                .andExpect(jsonPath("$.externalLinks").value(0))
                .andReturn();
    }
}