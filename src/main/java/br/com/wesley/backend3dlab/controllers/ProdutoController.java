package br.com.wesley.backend3dlab.controllers;

import br.com.wesley.backend3dlab.models.Produto;
import br.com.wesley.backend3dlab.payload.repository.ProdutosReposity;
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
public class ProdutoController {
    private static String PathImages = "/Users/Wesley/Documents/imagens/";
    @Autowired
    ProdutosReposity produtosReposity;

    @PostMapping(value  ="/produto",consumes = "multipart/form-data")
    public ResponseEntity<?> save (Produto produtoBody, @RequestParam("file") MultipartFile arquivo){
        if(produtosReposity.findByNome(produtoBody.getNome()) == null){
            produtosReposity.save(produtoBody);
        }else{
            return new ResponseEntity<>("Já existe um produto com este nome",HttpStatus.BAD_REQUEST);
        }
        try{
            if(!arquivo.isEmpty()){
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(PathImages+String.valueOf(produtoBody.getId())+arquivo.getOriginalFilename());
                Files.write(caminho,bytes);
                produtoBody.setNomeImagem(String.valueOf(produtoBody.getId())+arquivo.getOriginalFilename());
                produtosReposity.save(produtoBody);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Produto cadastrado com sucesso",HttpStatus.OK);
    }

    @GetMapping("/produto")
    public List<Produto> listar (){
        List<Produto> produtos = produtosReposity.findAll();
        return produtos;
    }

    @PutMapping("/produto/{idProduto}")
    public ResponseEntity<?> update(@PathVariable("idProduto") Long idProduto,@RequestBody Produto produtoBody){
        return produtosReposity.findById(idProduto)
                .map(produto ->{
                    produto.setNome(produtoBody.getNome());
                    produto.setValor(produtoBody.getValor());
                    produto.setDescricao((produtoBody.getDescricao()));
                    Produto produtoUpdated = produtosReposity.save(produto);
                    return ResponseEntity.ok().body(produtoUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/produto/{idProduto}")
    public ResponseEntity<Optional<Produto>> getProduto (@PathVariable Long idProduto){
        Optional<Produto> produto = produtosReposity.findById(idProduto);
        return new ResponseEntity<>(produto,HttpStatus.OK);
    }

    @GetMapping("/produto/img/{NomeImagem}")
    @ResponseBody
    public byte[] getImage (@PathVariable("NomeImagem") String NomeImagem) throws IOException {
        File imagemArquivo = new File(PathImages+NomeImagem);
        return Files.readAllBytes(imagemArquivo.toPath());
    }

}
