package br.com.iteris.universidade.testes.controller;

import br.com.iteris.universidade.testes.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/criptografia")
public class CriptografiaController {
    @PostMapping
    public ResponseEntity<String> crpitografa(@RequestParam("input") String input ){
        return ResponseEntity.ok(Criptografia.criptografar(input));
    }
}
