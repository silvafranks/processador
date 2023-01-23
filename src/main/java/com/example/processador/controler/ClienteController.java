package com.example.processador.controler;
import com.example.processador.model.cliente.dto.ClienteCriacaoDto;
import com.example.processador.model.cliente.services.ClienteQueryService;
import com.example.processador.model.cliente.services.ClienteServiceCreate;
import com.example.processador.model.transacao.Transacao;
import com.example.processador.model.transacao.service.TransacaoServiceQuery;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses({
            @ApiResponse(content = @Content(),
                    responseCode = "201", description = "Album criado com sucesso")
//            ,
//            @ApiResponse(content = @Content(),
//                    responseCode = "422", description = "Usuário não é um vendedor"),
//            @ApiResponse(content = @Content(),
//                    responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<?> criarCliente(@RequestBody ClienteCriacaoDto clienteCriacaoDto) {
        clienteServiceCreate.criarCliente(clienteCriacaoDto);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({
            @ApiResponse(content = @Content(),
                    responseCode = "200", description = "Todos os clientes")
//            ,
//            @ApiResponse(content = @Content(),
//                    responseCode = "422", description = "Usuário não é um vendedor"),
//            @ApiResponse(content = @Content(),
//                    responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping
    public ResponseEntity<?> buscarClientes() {
        return ResponseEntity.ok(clienteQueryService.allClientes());
    }


    @ApiResponses({
            @ApiResponse(content = @Content(),
                    responseCode = "200", description = "Buscar um cliente")
//            ,
//            @ApiResponse(content = @Content(),
//                    responseCode = "422", description = "Usuário não é um vendedor"),
//            @ApiResponse(content = @Content(),
//                    responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCliente(@PathVariable Integer id,@RequestHeader("Authorization") String bearerToken) {
        return ResponseEntity.ok(clienteQueryService.BuscarPorId(id,bearerToken));
    }

//    @GetMapping("{idCliente}/totalpatrimonio/")
//    public ResponseEntity totalPatrimonio(@PathVariable Integer idCliente){
//
//        return  ResponseEntity.ok(clienteQueryService.BuscarPatrimonios(idCliente));
//
//    }

    @ApiResponses({
            @ApiResponse(content = @Content(),
                    responseCode = "200", description = "extrato de cliente")
//            ,
//            @ApiResponse(content = @Content(),
//                    responseCode = "422", description = "Usuário não é um vendedor"),
//            @ApiResponse(content = @Content(),
//                    responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<Transacao>> extratoCliente(@PathVariable Integer id,@RequestHeader("Authorization") String bearerToken) {

        return ResponseEntity.ok(transacaoServiceQuery.getTransacoes(id,bearerToken));
    }
    public static void main(String[] args) {
        double gravity = -9.81;
        double initialVelocity = 0.0;
        double fallingTime = 10.0;
        double initialPosition = 0.0;
        double finalPosition = .5 * gravity * fallingTime *
                fallingTime;

        finalPosition = finalPosition + initialVelocity * fallingTime;
        finalPosition = finalPosition + initialPosition;

        System.out.println("Posição de um obj depois de " +
                fallingTime + " segundos é : " +
                finalPosition + " m.");
    }
}
