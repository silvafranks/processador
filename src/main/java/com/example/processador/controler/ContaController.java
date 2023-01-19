package com.example.processador.controler;

import com.example.processador.model.conta.dto.ContaCriacaoDto;
import com.example.processador.model.conta.services.ContaServiceCreate;
import com.example.processador.model.conta.services.ContaServiceMutation;
import com.example.processador.model.conta.services.ContaServiceQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContaController {

    private final ContaServiceCreate contaService;

    private final ContaServiceQuery contaServiceQuery;


    private final ContaServiceMutation contaServiceMutation;


    @PostMapping(value = "/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> criarConta(@PathVariable Integer idCliente,
                                        @RequestBody ContaCriacaoDto contaCriacaoDto,
                                        @RequestHeader("Authorization") String bearerToken){

        contaService.criarConta(idCliente,contaCriacaoDto, bearerToken);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{idCliente}")
    public ResponseEntity<?> buscarContas(@PathVariable Integer idCliente,@RequestHeader("Authorization") String bearerToken ){

        return ResponseEntity.ok(contaServiceQuery.contas(idCliente, bearerToken));
    }



}
