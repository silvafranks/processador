package com.example.processador.controler;

import com.example.processador.config.authetication.AuthenticationRequest;
import com.example.processador.config.authetication.AuthenticationResponse;
import com.example.processador.config.RegisterRequest;
import com.example.processador.config.authetication.AutheticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor

public class AutheticationController {
    private  final AutheticationService autheticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
    return ResponseEntity.ok(autheticationService.register(request));
    }

    @PostMapping("/autheticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){

        return ResponseEntity.ok(autheticationService.autheticate(request));

    }
}
