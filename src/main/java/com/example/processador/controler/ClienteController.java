package com.example.processador.controler;

import com.example.processador.model.cliente.dto.ClienteCriacaoDto;
import com.example.processador.model.cliente.services.ClienteQueryService;
import com.example.processador.model.cliente.services.ClienteServiceCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteController {

    private final ClienteServiceCreate clienteServiceCreate;

    private final ClienteQueryService clienteQueryService;

    @PostMapping
    public ResponseEntity<?> criarCliente(@RequestBody ClienteCriacaoDto clienteCriacaoDto) {
        System.out.println(clienteCriacaoDto);
        return ResponseEntity.ok(clienteServiceCreate.criarCliente(clienteCriacaoDto));
    }

    @GetMapping
    public  ResponseEntity<?> buscarClientes(){
     return ResponseEntity.ok(clienteQueryService.allClientes());
    }


    @GetMapping("/{id}")
    public  ResponseEntity<?> buscarCliente(@PathVariable Integer id){
        return ResponseEntity.ok(clienteQueryService.BuscarPorId(id));
    }
}
