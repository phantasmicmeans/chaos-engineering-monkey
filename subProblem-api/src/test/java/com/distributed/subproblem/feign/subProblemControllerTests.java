package com.distributed.subproblem.feign;

import com.distributed.subproblem.domain.subProblem;
import com.distributed.subproblem.exception.DataInvalidException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class subProblemControllerTests {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    subProblemService subProblemService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setUp(){
        this.subProblemService.deleteAll();
    }

    @Test//정상적 post test
    public void createSubProblem() throws Exception {

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        subProblem problem = subProblem.builder()
                        .id(1L)
                        .code("abcdef")
                        .content("hello-world")
                        .count(0)
                        .createdTimeAt(localDateTime)
                        .createdDateAt(localDate)
                        .build();
        //MediaType.APPLICATION_JSON = MediaType.class, APPLICATION_JSON_UTF8_VALUE = String
        this.mockMvc.perform(post("/api/v1/sub-problem")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(problem)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test //id로 조회하기
    public void getSubProblemById() throws Exception{

        this.mockMvc.perform(get("/api/v1/sub-problem/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("count").exists())
                .andExpect(jsonPath("createdTimeAt").exists())
                .andExpect(jsonPath("createdDateAt").exists());
    }

    @Test
    public void getSubProblemByCode() throws Exception{

        this.mockMvc.perform(get("/api/v1/sub-problems/abcdef"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("count").exists())
                .andExpect(jsonPath("createdTimeAt").exists())
                .andExpect(jsonPath("createdDateAt").exists());

    }


    @Test //data null or wrong
    public void createSubProblemFail() throws Exception {

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        subProblem problem = subProblem.builder()
                .code("abcdef") //content null
                .count(0)
                .createdTimeAt(localDateTime)
                .createdDateAt(localDate)
                .build();

        //content must not be null;
        this.mockMvc.perform(post("/api/v1/sub-problem")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(problem)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test //code data invalid, code length must > 3 && not null
    public void createSubProblemFailWithCode() throws Exception {

        subProblem problem = new subProblem("a","hello-world");
        //code must be length 6
        this.mockMvc.perform(post("/api/v1/sub-problem")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(problem)))
                .andDo(print())
                .andExpect(status().is(400));
    }
}

