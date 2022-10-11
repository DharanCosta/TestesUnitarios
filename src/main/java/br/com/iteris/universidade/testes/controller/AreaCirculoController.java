package br.com.iteris.universidade.testes.controller;

import br.com.iteris.universidade.testes.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/area-circulo")
public class AreaCirculoController {

    @PostMapping
    public ResponseEntity<String> calcula(@RequestParam("raio") double raio) {
        return ResponseEntity.ok(AreaCirculo.calcula(raio));
    }
}
