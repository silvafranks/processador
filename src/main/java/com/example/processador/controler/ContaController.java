package com.example.processador.controler;

import com.example.processador.model.conta.dto.ContaCriacaoDto;
import com.example.processador.model.conta.dto.ContaMutationDto;
import com.example.processador.model.conta.services.ContaServiceCreate;
import com.example.processador.model.conta.services.ContaServiceMutation;
import com.example.processador.model.conta.services.ContaServiceQuery;
import com.example.processador.model.patrimonio.Patrimonio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContaController {

    private final ContaServiceCreate contaService;

    private final ContaServiceQuery contaServiceQuery;


    private final ContaServiceMutation contaServiceMutation;

    @PostMapping(value = "/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> criarConta(@PathVariable Integer idCliente,
                                        @RequestBody ContaCriacaoDto contaCriacaoDto){
        System.out.println(contaCriacaoDto);

        contaService.criarConta(idCliente,contaCriacaoDto);


        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{idCliente}")
    public ResponseEntity<Optional<Patrimonio>> buscarConta(@PathVariable Integer idCliente ){

        return ResponseEntity.ok(contaServiceQuery.contas(idCliente));
    }

//    @PatchMapping(value = "/{idCliente}/{idConta}")
//    public ResponseEntity depositarEmUmaConta(@PathVariable Integer idCliente,
//                                              @PathVariable Integer idConta,
//                                              @RequestBody ContaMutationDto valor){
//        contaServiceMutation.alterarValorConta(idCliente,idConta,valor);
//        return ResponseEntity.ok().build();
//    }
//
//    @PatchMapping(value = "/{idcliente}/{idcontaEntrada}/{idcontasaida}")
//    public  ResponseEntity trasferirEntreContas(@PathVariable Integer idcliente,
//                                                @PathVariable Integer idContaEntrada,
//                                                @PathVariable Integer idContaSaida,
//                                                @RequestBody ValorTransferencia valorTransferencia
//                                                ){
//
//    }
}
