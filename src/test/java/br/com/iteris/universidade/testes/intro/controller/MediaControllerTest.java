package br.com.iteris.universidade.testes.intro.controller;

import br.com.iteris.universidade.testes.controller.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.test.web.servlet.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MediaController.class)
public class MediaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @ParameterizedTest
    @CsvSource(value = {"2;2;2,0", "2;4;3,0", "2.5;5.2;3,9"}, delimiter = ';')
    void shouldCalcularMediaComDoisParametros(double n1, double n2, String expectedResult) throws Exception{
        final var expectedContentType = "text/plain;charset=UTF-8";

        var mvcResult = mockMvc.perform(
                post("/media/2")
                        .param("n1",String.valueOf(n1))
                        .param("n2",String.valueOf(n2))
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andReturn();
        var actualResponse = mvcResult.getResponse().getContentAsString();

        assertEquals(expectedResult,actualResponse);
    }
    
    @ParameterizedTest
    @CsvSource(value = {"5;6;7;6,0", "5;10;10;8,3", "10.5;6.2;7.8;8,2"}, delimiter = ';')
    void shouldCalcularMediaComTresParametros(double n1, double n2, double n3, String expectedResult) throws Exception{
        final var expectedContentType = "text/plain;charset=UTF-8";

        var mvcResult = mockMvc.perform(
                post("/media/3")
                        .param("n1", String.valueOf(n1))
                        .param("n2", String.valueOf(n2))
                        .param("n3", String.valueOf(n3))
        )
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andReturn();
        var actuaResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResult,actuaResponse);
    }

    @ParameterizedTest
    @CsvSource(value = {"2;-2", "-2;4", "-2;-4"}, delimiter = ';')
    void shouldCalcularMediaDoisParamsThenThrowIllegalArgumentException(double n1, double n2) throws Exception{
        final var expectedContentType = "application/json";
        var expectedMessage = "Os valores devem ser positivos.";

        var mvcResult = mockMvc.perform(
                        post("/media/2")
                                .param("n1",String.valueOf(n1))
                                .param("n2",String.valueOf(n2))
                )
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andExpect(result -> assertEquals(expectedMessage, result.getResolvedException().getMessage()))
                .andReturn();
    }

    @ParameterizedTest
    @CsvSource(value = {"-2;2;1", "2;-4;1", "2;4;-1", "-2;-4;1", "2;-4;-1", "-2;4;-1", "-2;-4;-1"}, delimiter = ';')
    void shouldCalcularMediaTresParamsThenThrowIllegalArgumentException(double n1, double n2, double n3) throws Exception{
        final var expectedContentType = "application/json";
        var expectedMessage = "Os valores devem ser positivos.";

        var mvcResult = mockMvc.perform(
                        post("/media/3")
                                .param("n1",String.valueOf(n1))
                                .param("n2",String.valueOf(n2))
                                .param("n3",String.valueOf(n3))
                )
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andExpect(result -> assertEquals(expectedMessage, result.getResolvedException().getMessage()))
                .andReturn();
    }

}
