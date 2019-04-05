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
                .header("Authorization", getAdminAuthorityToken())
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
                .header("Authorization", getDomainAuthorityToken())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status()
                        .isForbidden());
    }


      private static List<UserDto> getMockUserEntityList() {
        return new ArrayList<>(Arrays.asList(
                new UserDto(1L, "admin_jack", "admin123",
                        "Albert", "Stec", "mail@mial.mail", emptySet()),
                new UserDto(2L, "user_mathew", "user123",
                        "Kamil", "Kot", "mail@mial.pl", emptySet()),
                new UserDto(3L, "moderator_philip", "moderator123",
                        "Maciej", "Smith", "mail@mial.com", emptySet())
        ));
    }

    private static String getDomainAuthorityToken() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                ".eyJzdWIiOiJlbG8iLCJleHAiOjE1NTQ3MTE3MDAsImF1dGhvcml0aWVzIjpbIlVTRVIiXX0" +
                ".9Tv_0eXTvV3v4R-Q5zjv_EYRRikhQq2lVNND4S7mMNHKaT0lRCszQu0WmEC0bYuphB4nHqoW9STJ6oAv_jSqJg";
    }

    private static String getAdminAuthorityToken() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                ".eyJzdWIiOiJzdXBlci11c2VyIiwiZXhwIjoxNTU0NzExNjM0LCJhdXRob3JpdGllcyI6WyJBRE1JTiIsIkRPTUFJTiIsIk1PREVSQVRPUiJdfQ" +
                ".L6vCr76bkuuAIoBy3BtA7kjNrO7tWevvM3atAqr86dLh8waHSMY3X0Lj1OZMk3Gg4mx9CgRxvSA90x1Ap3UP5Q";
    }
}
