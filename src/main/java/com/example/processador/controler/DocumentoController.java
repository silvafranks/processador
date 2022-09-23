package com.example.processador.controler;

import com.example.processador.service.DocumentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/documentos")
@RestController
public class DocumentoController {

    private DocumentoService documentoService;

    @GetMapping
    public ResponseEntity<?> getMappping() {
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/{nomeArquivo}/upload")
    public ResponseEntity<?> postFile(@PathVariable String nomeArquivo) throws IOException {
        DocumentoService.guardaArquivo(nomeArquivo);
        return ResponseEntity.ok("upload feito com sucesso!");
    }
}
