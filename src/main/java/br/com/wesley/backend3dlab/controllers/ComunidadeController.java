package br.com.wesley.backend3dlab.controllers;

import br.com.wesley.backend3dlab.models.Comunidade;
import br.com.wesley.backend3dlab.payload.repository.ComunidadeReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ComunidadeController {
    private static String PathImages = "/Users/Wesley/Documents/imagens/";
    @Autowired
    ComunidadeReposity comunidadeReposity;

    @PostMapping(value  ="/comunidade",consumes = "multipart/form-data")
    public ResponseEntity<?> save (Comunidade ComunidadeBody, @RequestParam("file") MultipartFile arquivo){
        try{
            if(!arquivo.isEmpty()){
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(PathImages+String.valueOf(ComunidadeBody.getId())+arquivo.getOriginalFilename());
                Files.write(caminho,bytes);
                ComunidadeBody.setNomeImagem(String.valueOf(ComunidadeBody.getId())+arquivo.getOriginalFilename());
                comunidadeReposity.save(ComunidadeBody);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Criação cadastrada com sucesso",HttpStatus.OK);
    }

    @GetMapping("/comunidade")
    public List<Comunidade> listar (){
        List<Comunidade> comunidade = comunidadeReposity.findAll();
        return comunidade;
    }

    @PutMapping("/comunidade/{idCriacao}")
    public ResponseEntity<?> update(@PathVariable("idCriacao") Long idProduto,@RequestBody Comunidade cricaoBody){
        return comunidadeReposity.findById(idProduto)
                .map(criacao ->{
                    criacao.setNome(cricaoBody.getNome());
                    criacao.setAutor(cricaoBody.getAutor());
                    Comunidade produtoUpdated = comunidadeReposity.save(criacao);
                    return ResponseEntity.ok().body(produtoUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/comunidade/{idCriacao}")
    public ResponseEntity<Optional<Comunidade>> getProduto (@PathVariable Long idCriacao){
        Optional<Comunidade> cricao = comunidadeReposity.findById(idCriacao);
        return new ResponseEntity<>(cricao,HttpStatus.OK);
    }

    @GetMapping("/comunidade/img/{NomeImagem}")
    @ResponseBody
    public byte[] getImage (@PathVariable("NomeImagem") String NomeImagem) throws IOException {
        File imagemArquivo = new File(PathImages+NomeImagem);
        return Files.readAllBytes(imagemArquivo.toPath());
    }

}
