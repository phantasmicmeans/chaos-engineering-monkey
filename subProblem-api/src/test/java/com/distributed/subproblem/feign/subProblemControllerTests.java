package com.distributed.subproblem.feign;

import com.distributed.subproblem.domain.subProblem;
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

        String code = "abcdef";
        subProblem problem = subProblem.builder()
                        .id(1)
                        .code(code)
                        .pro_hash(String.valueOf(code.hashCode()))
                        .content("hello-world")
                        .count(0)
                        .createdTimeAt(localDateTime)
                        .createdDateAt(localDate)
                        .build();
        //MediaType.APPLICATION_JSON = MediaType.class, APPLICATION_JSON_UTF8_VALUE = String
        this.mockMvc.perform(post("/v1/sub-problem")
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

        subProblem sProblem = generateCreation("abcdef");
        this.mockMvc.perform(get("/v1/sub-problem/{id}",sProblem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pro_hash").exists())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.count").exists())
                .andExpect(jsonPath("$.createdTimeAt").exists())
                .andExpect(jsonPath("$.createdDateAt").exists());
    }

    @Test //id가 없는 경우 조회, 404
    public void getSubProblem404() throws Exception {

        this.mockMvc.perform(get("/v1/sub-problem/{id}", 12334))
                .andExpect(status().isNotFound());
    }

    @Test //code가 없는 경우 조회, 404
    public void getSubProblemByCode404() throws Exception{

        String code = "abcdeg";
        this.mockMvc.perform(get("v1/sub-problems/{code}", code))
                .andExpect(status().isNotFound());

    }

    @Test //code length 6이 아닌 경우 조회하기, 400
    public void getSubProblemByCode400() throws Exception {

        String code = "ab";
        String mustResponse = "code length is short. length must be 6, please check your {code}";

        this.mockMvc.perform(get("/v1/sub-problem/list/{code}", code))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(mustResponse));

    }

    @Test //content가 null인 경우, 400 & error message
    public void createSubProblemFail() throws Exception {

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        String code = "abcdef";
        subProblem problem = subProblem.builder()
                .code(code) //content null
                .pro_hash(String.valueOf(code.hashCode()))
                .count(0)
                .createdTimeAt(localDateTime)
                .createdDateAt(localDate)
                .build();

        //content must not be null;
        String mustResponse = "content must not be null";
        this.mockMvc.perform(post("/v1/sub-problem")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(problem)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(mustResponse))
                .andReturn();

    }

    @Test //code data invalid, code length must == 6 , 400 & error message
    public void createSubProblemFailWithCode() throws Exception {

        subProblem problem = new subProblem("a","hello-world");
        //code must be length 6
        String mustResponse = "code length is short. length must be 6, please check your {code}";

        this.mockMvc.perform(post("/v1/sub-problem")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(problem)))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value(mustResponse))
                .andReturn();

    }

    private subProblem generateCreation(String code) {

        subProblem sProblem = subProblem.builder()
                .code(code)
                .pro_hash(String.valueOf(code.hashCode()))
                .content("hello-world")
                .count(0)
                .createdTimeAt(LocalDateTime.now())
                .createdDateAt(LocalDate.now())
                .build();

        return (subProblem)this.subProblemService.saveSubProblem(sProblem);
    }
}

