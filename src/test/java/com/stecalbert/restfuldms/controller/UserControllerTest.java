package com.stecalbert.restfuldms.controller;

import com.stecalbert.restfuldms.model.dto.UserDto;
import com.stecalbert.restfuldms.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptySet;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private static final String DOMAIN_TOKEN =
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                    ".eyJzdWIiOiJlbG8iLCJleHAiOjE1NTQ0MDk4MDZ9" +
                    ".exgHQqyAjMeRmbHT8ii2eE1iaLLKtnHaQxDkXJdBDLSCaWqx1Qy314SvPUGAKm7IT1hcLuydOso9JtEjOWUnLw";

    private static final String ADMIN_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
            ".eyJzdWIiOiJzdXBlci11c2VyIiwiZXhwIjoxNTU0NjU3MzcxLCJhdXRob3JpdGllcyI6WyJBRE1JTiIsIkRPTUFJTiIsIk1PREVSQVRPUiJdfQ" +
            ".B_obMAWv8rmeMXqO8KfFOOif40Btgq5hvr5EACeXiuXuezC_WB8TErJbpMELYfuxF_3586P_lgJGzN7qh0uxJQ";

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenInvalidToken_whenGetAllUsers_thenForbidden() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(getMockUserEntityList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status()
                        .isForbidden());
    }

    @Test
    public void givenValidToken_whenGetAllUsers_thenOk() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(getMockUserEntityList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void givenValidTokenIncorrectRole_whenGetAllUsers_thenForbidden() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(getMockUserEntityList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users")
                .header("Authorization", DOMAIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status()
                        .isForbidden());
    }


    private List<UserDto> getMockUserEntityList() {
        return new ArrayList<>(Arrays.asList(
                new UserDto(1L, "admin_jack", "admin123", emptySet()),
                new UserDto(2L, "user_mathew", "user123", emptySet()),
                new UserDto(3L, "moderator_philip", "moderator123", emptySet())
        ));
    }
}