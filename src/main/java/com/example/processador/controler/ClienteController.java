package com.example.processador.controler;
import com.example.processador.model.cliente.dto.ClienteCriacaoDto;
import com.example.processador.model.cliente.services.ClienteQueryService;
import com.example.processador.model.cliente.services.ClienteServiceCreate;
import com.example.processador.model.transacao.Transacao;
import com.example.processador.model.transacao.service.TransacaoServiceQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class ClienteController {

    private final ClienteServiceCreate clienteServiceCreate;

    private final ClienteQueryService clienteQueryService;

    private final TransacaoServiceQuery transacaoServiceQuery;

    @PostMapping
    public ResponseEntity<?> criarCliente(@RequestBody ClienteCriacaoDto clienteCriacaoDto) {
        clienteServiceCreate.criarCliente(clienteCriacaoDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> buscarClientes() {
        return ResponseEntity.ok(clienteQueryService.allClientes());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCliente(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteQueryService.BuscarPorId(id));
    }

//    @GetMapping("{idCliente}/totalpatrimonio/")
//    public ResponseEntity totalPatrimonio(@PathVariable Integer idCliente){
//
//        return  ResponseEntity.ok(clienteQueryService.BuscarPatrimonios(idCliente));
//
//    }

    @GetMapping("/{idcliente}/extrato/")
    public ResponseEntity<List<Transacao>> extratoCliente(@PathVariable Integer idcliente) {

        return ResponseEntity.ok(transacaoServiceQuery.getTransacoes(idcliente));
    }

}
