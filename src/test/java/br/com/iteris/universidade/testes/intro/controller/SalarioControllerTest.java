package br.com.iteris.universidade.testes.intro.controller;

import br.com.iteris.universidade.testes.controller.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SalarioController.class)
public class SalarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource(value = {"500;1230.3;684,55", "700;150;722,50", "1700;1230.5;1.884,58"}, delimiter = ';')
    void Quando_PassadoValoresValidos_Deve_RetornarSalarioComBonus(double salario, double vendas, String expected) throws Exception {
        final var expectedContentType = "text/plain;charset=UTF-8";

        var mvcResult = mockMvc.perform(
                        post("/salario")
                                .param("salario", String.valueOf(salario))
                                .param("vendas", String.valueOf(vendas))
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andReturn();

        var actualResponse = mvcResult.getResponse().getContentAsString();

        assertEquals(expected, actualResponse);
    }

    @ParameterizedTest
    @CsvSource(value = {"500;0;500,00", "700;0;700,00", "1700;0;1.700,00"}, delimiter = ';')
    void Quando_PassadoVendasZeradas_Deve_RetornarSalarioSemBonus(double salario, double vendas, String expected) throws Exception {
        final var expectedContentType = "text/plain;charset=UTF-8";

        var mvcResult = mockMvc.perform(
                        post("/salario")
                                .param("salario", String.valueOf(salario))
                                .param("vendas", String.valueOf(vendas))
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andReturn();

        var actualResponse = mvcResult.getResponse().getContentAsString();

        assertEquals(expected, actualResponse);
    }

    @ParameterizedTest
    @CsvSource(value = {"500;-52564.45", "700;-1", "1700;-0.99"}, delimiter = ';')
    void Quando_PassadoVendasInvalidas_Deve_RetornarExcecaoVendas(double salario, double vendas) throws Exception {
        final var expectedContentType = "application/json";
        final var expectedMessage = "Vendas não podem ser negativas.";

        mockMvc.perform(
                        post("/salario")
                                .param("salario", String.valueOf(salario))
                                .param("vendas", String.valueOf(vendas))
                )
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andExpect(result -> assertEquals(expectedMessage, result.getResolvedException().getMessage()))
                .andReturn();
    }

    @ParameterizedTest
    @CsvSource(value = {"-700;1230.3", "0;0", "499.999;1230.5"}, delimiter = ';')
    void Quando_PassadoSalarioInvalido_Deve_RetornarExcecaoSalario(double salario, double vendas) throws Exception {
        final var expectedContentType = "application/json";
        final var expectedMessage = "Salário deve ser válido, acima do piso de 500.";

        mockMvc.perform(
                        post("/salario")
                                .param("salario", String.valueOf(salario))
                                .param("vendas", String.valueOf(vendas))
                )
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andExpect(result -> assertEquals(expectedMessage, result.getResolvedException().getMessage()))
                .andReturn();
    }

}
