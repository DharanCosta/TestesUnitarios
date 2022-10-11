package br.com.iteris.universidade.testes.controller;

import br.com.iteris.universidade.testes.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salario")
public class SalarioController {
    @PostMapping
    public ResponseEntity<String> calcula(@RequestParam("salario") double salario, @RequestParam("vendas") double vendas) {
        return ResponseEntity.ok(Salario.comBonus(salario, vendas));
    }
}
