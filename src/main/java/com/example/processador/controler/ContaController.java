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


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContaController {

    private final ContaServiceCreate contaService;

    private final ContaServiceQuery contaServiceQuery;


    private final ContaServiceMutation contaServiceMutation;

    @RequestMapping("/jsp")
    public ModelAndView jspTeste(){

        return new ModelAndView("index");
    }

    @PostMapping(value = "/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> criarConta(@PathVariable Integer idCliente,
                                        @RequestBody ContaCriacaoDto contaCriacaoDto){
        contaService.criarConta(idCliente,contaCriacaoDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{idCliente}")
    public ResponseEntity<?> buscarContas(@PathVariable Integer idCliente ){

        return ResponseEntity.ok(contaServiceQuery.contas(idCliente));
    }



}
