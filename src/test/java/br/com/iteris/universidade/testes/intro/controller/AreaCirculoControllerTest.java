package br.com.iteris.universidade.testes.intro.controller;

import br.com.iteris.universidade.testes.controller.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.test.web.servlet.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AreaCirculoController.class)
class AreaCirculoControllerTest {
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void shouldCalcularAreaCirculoThenReturnValidArea() throws Exception {
//        final var expectedAreaCirculo = "125.663,7061";
//        final var expectedContentType = "text/plain;charset=UTF-8";
//        final var expectedContentLength = "12";
//
//        var mvcResult = mockMvc.perform(
//                        post("/area-circulo")
//                                .param("raio", "200")
//                )
//                .andExpect(status().isOk())
//                .andExpect(header().string("Content-Type", expectedContentType))
//                .andExpect(header().string("Content-Length", expectedContentLength))
//                .andReturn();
//
//        var actualResponse = mvcResult.getResponse().getContentAsString();
//
//        assertEquals(expectedAreaCirculo, actualResponse);
//    }

    @ParameterizedTest
    @CsvSource(value = {"2;12,5664", "100.64;31.819,3372", "150;70.685,8347"}, delimiter = ';')
    void shouldCalcularAreaCirculoThenReturnAreaValida(double raio, String expectedArea) throws Exception {
        final var expectedContentType = "text/plain;charset=UTF-8";

        var mvcResult = mockMvc.perform(
                        post("/area-circulo")
                                .param("raio", String.valueOf(raio))
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andReturn();

        var actualResponse = mvcResult.getResponse().getContentAsString();

        assertEquals(expectedArea, actualResponse);
    }

    @Test
    void shouldCalcularAreaCirculoThenReturnZero() throws Exception {
        final var expectedContentType = "text/plain;charset=UTF-8";
        var expectedArea = "0";

        var mvcResult = mockMvc.perform(
                        post("/area-circulo")
                                .param("raio", "0")
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andReturn();

        var actualResponse = mvcResult.getResponse().getContentAsString();

        assertEquals(expectedArea, actualResponse);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-2, -2.2564})
    void shouldCalcularAreaThenThrowIllegalArgumentException(double raio) throws Exception {
        final var expectedContentType = "application/json";
        var expectedMessage = "Raio deve ser positivo.";

        mockMvc.perform(
                        post("/area-circulo")
                                .param("raio", String.valueOf(raio))
                )
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andExpect(result -> assertEquals(expectedMessage, result.getResolvedException().getMessage()))
                .andReturn();
    }
}
