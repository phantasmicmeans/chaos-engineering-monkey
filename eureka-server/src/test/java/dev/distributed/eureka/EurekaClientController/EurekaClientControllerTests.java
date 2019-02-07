package dev.distributed.eureka.EurekaClientController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class EurekaClientControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getRegionsForClients() {
    //1. regions/clients -> eureka server에 등록된 regions

//        this.mockMvc.perform(get("/regions/clients"))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath())
    }

    /*
    API 목록
   1. regions/clients -> eureka server에 등록된 regions
   2. clients/applications/{applicationName}
        -> Application Namel 존재하는지 확인, 존재하면 이름 return;
   3. clients/applications/{applicationName}/detail
        -> 2번의 API로 application Name존재 유무 확인,
           Name 존재 시 이 API 호출 - > detail 전부세
   4. regions/{regions}/clients/applications
        -> Application들의 regions 종류 명세
     */
}
