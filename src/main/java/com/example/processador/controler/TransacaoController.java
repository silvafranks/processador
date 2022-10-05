package com.example.processador.controler;

import com.example.processador.model.conta.services.ContaServiceMutation;
import com.example.processador.model.transacao.Dto.TransacaoCriacaoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransacaoController {

    private final ContaServiceMutation contaServiceMutation;

    @PatchMapping(value = "/{idCliente}/{idConta}")
    public ResponseEntity depositarEmUmaConta(@PathVariable Integer idCliente,
                                              @PathVariable Integer idConta,
                                              @RequestBody TransacaoCriacaoDto valor){
        contaServiceMutation.depositar(idCliente,idConta,valor);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(value = "/{idCliente}/{idConta}")
    public ResponseEntity retirarDinheiro(@PathVariable Integer idCliente,
                                          @PathVariable Integer idConta,
                                          @RequestBody TransacaoCriacaoDto valor){
        contaServiceMutation.retirar(idCliente,idConta, valor);
        return ResponseEntity.ok().build();
    }


    @PatchMapping(value = "/{idClienteSaida}/{idContaSaida}/{idClienteEntrada}/{idContaEntrada}")
    public  ResponseEntity transferirEntreClientes(@PathVariable Integer idClienteSaida,
                                                 @PathVariable Integer idContaSaida,
                                                 @PathVariable Integer idClienteEntrada,
                                                 @PathVariable Integer idContaEntrada,
                                                 @RequestBody TransacaoCriacaoDto valorTransferencia){

        contaServiceMutation.transferenciaEntreClientes(idClienteSaida,
                                                        idContaSaida,
                                                        idClienteEntrada,
                                                        idContaEntrada,
                                                        valorTransferencia);
      return  ResponseEntity.ok().build();

    }
    @PatchMapping(value = "/{idCliente}/{idContaSaida}/{idContaEntrada}")
    public  ResponseEntity transferirEntreContas(@PathVariable Integer idCliente,
                                                 @PathVariable Integer idContaSaida,
                                                 @PathVariable Integer idContaEntrada,
                                                 @RequestBody TransacaoCriacaoDto valorTransferencia){

        contaServiceMutation.transferenciaInternaDeContas(idCliente,
                idContaSaida,
                idContaEntrada,
                valorTransferencia);
        return  ResponseEntity.ok().build();

    }
}
