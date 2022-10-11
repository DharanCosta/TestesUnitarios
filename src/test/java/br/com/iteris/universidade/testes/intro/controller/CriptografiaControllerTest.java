package br.com.iteris.universidade.testes.intro.controller;

import br.com.iteris.universidade.testes.controller.*;
import br.com.iteris.universidade.testes.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CriptografiaController.class)
public class CriptografiaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Criptografia criptografia;

    @ParameterizedTest
    @CsvSource(value = {"Texto #3;3# rvzgV", "abcABC1;1FECedc", "vxpdylY .ph;ks. \\n{frzx", "vv.xwfxo.fd;gi.r{hyz-xx"}, delimiter = ';')
    void shouldCriptografarERetornarCriptografado(String entrada, String expected) throws Exception{
        final var expectedContentType = "text/plain;charset=UTF-8";
               var mvcResult = mockMvc.perform(

                        post("/criptografia")
                                .param("input",String.valueOf(entrada))
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andReturn();
        var actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expected,actualResponse);

    }
    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"Tex", " "})
    void shouldCriptografarThenThrowIllegalArgumentException(String entrada) throws Exception {
        final var expectedContentType = "application/json";
        final var expectedMessage = "Entrada é nula, vazia ou muito pequena.";

        mockMvc.perform(
                        post("/criptografia")
                                .param("input", entrada)
                )
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", expectedContentType))
                .andExpect(result -> assertEquals(expectedMessage, result.getResolvedException().getMessage()))
                .andReturn();
    }

    @Test
    void shouldRetornarOCharMaisTresSeForLetra(){
        var s = "y";
        var expectedResult = "|";
        var actualPosition = 0;

        Map<Integer, String> map = new HashMap<>();
        for (int i = 32; i <= 126; i++) {
            map.put(i ,Character.toString((char) i));
            if(i>64 && i<91 || i>96 && i<123) {
                if (map.get(i).equals(s)) {
                    actualPosition = i + 3;
                }
            } else {
                if (map.get(i).equals(s))
                    actualPosition = i;
            }
         }
        var actualResponse= map.get(actualPosition);
        assertEquals(expectedResult,actualResponse);
    }

    @Test
    void shouldRetornarOCharMenosUmChar(){
        var s = "D";
        var expectedResult = "C";
        var actualPosition = 0;

        Map<Integer, String> map = new HashMap<>();
        for (int i = 32; i <= 126; i++) {
            map.put(i ,Character.toString((char) i));
                if (map.get(i).equals(s)) {
                    actualPosition = i - 1;
                }
        }
        var actualResponse= map.get(actualPosition);
        assertEquals(expectedResult,actualResponse);
    }
    @Test
    public void shouldReturnStringCriptografada() {
        String entrada = "vv.xwfxo.fd";
        var expectedResult = "gi.r{hyz-xx";
        if (entrada == null || entrada.isBlank()  || entrada.length() < 4)
            throw new IllegalArgumentException("Entrada é nula, vazia ou muito pequena.");
        System.out.println("1ªVerificação :"+entrada);

        var primeira= "";
        final var entradaCaracteres = entrada.toCharArray();

        for (var caractere :entradaCaracteres) {
            var codigo = 0;

            if(Character.isLetter(caractere)) {
                codigo = caractere + 3;
            }else {
                codigo = caractere;
            }
            primeira += (char)codigo;
        }
        var primeiraArray = primeira.toCharArray();
        String segunda = new StringBuilder(new String(primeiraArray)).reverse().toString();

        System.out.println("Fase 2:"+segunda);

        int metade = (entrada.length()/ 2);
        System.out.println("Metade: "+metade);

        String terceira = segunda.substring(0, metade);

        for (int i = metade; i < segunda.length(); i++) {
            int codigo = segunda.charAt(i) - 1;
            terceira += (char)codigo;
        }
        System.out.println("Fase 3:"+terceira);

        assertEquals(expectedResult, terceira);
    }

}
