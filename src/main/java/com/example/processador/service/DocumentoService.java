package com.example.processador.service;

import com.example.processador.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class DocumentoService {


    public static void guardaArquivo(String nomeArquivo) throws IOException {
        System.out.println(nomeArquivo);
        File source = new File("/home/franklin/Downloads/" + nomeArquivo);

        if (!source.exists()){
            throw new EntidadeNaoEncontradaException("Arquivo não existe",source);
        }
        BasicFileAttributes attributes = Files.readAttributes(Path.of("/home/franklin/Downloads/" + nomeArquivo), BasicFileAttributes.class);

        FileTime date = attributes.creationTime();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String formated = localDateTime.format(DateTimeFormatter.ISO_DATE);

        File dest = new File("/home/franklin/Área de Trabalho/arquivos/" + formated + "-" + nomeArquivo);

        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
