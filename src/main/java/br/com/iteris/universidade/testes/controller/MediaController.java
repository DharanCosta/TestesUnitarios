package br.com.iteris.universidade.testes.controller;

import br.com.iteris.universidade.testes.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/media")
public class MediaController {

    @PostMapping("/2")
    public ResponseEntity<String> calculaMediaDoisParam(
            @RequestParam("n1") double n1,
            @RequestParam("n2") double n2) {
        return ResponseEntity.ok(Media.simples(n1, n2));
    }
    @PostMapping("/3")
    public ResponseEntity<String> calculaMediaTresParam(
            @RequestParam("n1") double n1,
            @RequestParam("n2") double n2,
            @RequestParam("n3") double n3) {
        return ResponseEntity.ok(Media.simples(n1, n2, n3));
    }
}
