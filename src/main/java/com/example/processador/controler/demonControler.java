package com.example.processador.controler;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class demonControler {
    @GetMapping
    public ResponseEntity<String> oi(@RequestHeader("Authorization") String bearerToken) {
        System.out.println("TOKEN: " + bearerToken);
        return ResponseEntity.ok("OIIII");
    }
}
