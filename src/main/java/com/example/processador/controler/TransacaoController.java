package com.example.processador.controler;

import com.example.processador.model.conta.dto.ContaMutationDto;
import com.example.processador.model.conta.services.ContaServiceMutation;
import com.example.processador.model.transacao.Dto.TransacaoCriacaoDto;
import com.example.processador.model.transacao.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransacaoController {

    private final ContaServiceMutation contaServiceMutation;
    private  final TransacaoRepository transacaoRepository;

    @PatchMapping(value = "/{idCliente}/{idConta}")
    public ResponseEntity depositarEmUmaConta(@PathVariable Integer idCliente,
                                              @PathVariable Integer idConta,
                                              @RequestBody ContaMutationDto valor){
        contaServiceMutation.alterarValorConta(idCliente,idConta,valor);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{idcliente}/{idcontaEntrada}/{idContaSaida}")
    public  ResponseEntity transferirEntreContas(@PathVariable Integer idcliente,
                                                @PathVariable Integer idcontaEntrada,
                                                @PathVariable Integer idContaSaida,
                                                @RequestBody TransacaoCriacaoDto valorTransferencia){
        contaServiceMutation.transferenciaInternaDeContas
                (idcliente,idContaSaida,idcontaEntrada,valorTransferencia);

      return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{idClienteSaida}/{idContaSaida}/{idClienteEntrada}/{idContaEntrada}")
    public  ResponseEntity transferirEntreContas(@PathVariable Integer idClienteSaida,
                                                 @PathVariable Integer idContaSaida,
                                                 @PathVariable Integer idClienteEntrada,
                                                 @PathVariable Integer idContaEntrada,
                                                 @RequestBody TransacaoCriacaoDto valorTransferencia){

        contaServiceMutation.transferenciaEntreContas(idClienteSaida,
                                                      idContaSaida,
                                                      idClienteEntrada,
                                                      idContaEntrada,
                                                      valorTransferencia);
      return   ResponseEntity.ok().build();

    }
}
