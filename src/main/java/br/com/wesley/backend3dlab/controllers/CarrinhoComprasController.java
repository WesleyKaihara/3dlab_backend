package br.com.wesley.backend3dlab.controllers;

import br.com.wesley.backend3dlab.payload.repository.ProdutosReposity;
import br.com.wesley.backend3dlab.payload.repository.UserRepository;
import br.com.wesley.backend3dlab.Error.ProductNotExistsException;
import br.com.wesley.backend3dlab.models.User;
import br.com.wesley.backend3dlab.models.CarrinhoCompras;
import br.com.wesley.backend3dlab.payload.repository.CarrinhoComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/carrinhoCompras")
public class CarrinhoComprasController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CarrinhoComprasRepository carrinhoComprasRepository;
    @Autowired
    ProdutosReposity produtoRepository;

    @GetMapping
    public ResponseEntity<?> listar (){
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Long userId = userRepository.findByUsername(username).get().getId();

            Iterable<CarrinhoCompras> carrinhoCompras = carrinhoComprasRepository.findAllByIdCliente(userId);
            return new ResponseEntity<>(carrinhoCompras, OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/isInCart/{idProduto}")
    public ResponseEntity<CarrinhoCompras> IsInCart (@PathVariable Long idProduto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).get();

        return new ResponseEntity<>(carrinhoComprasRepository.findByIdClienteAndIdProduto(user.getId(),idProduto),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CarrinhoCompras> adicionar (@RequestBody CarrinhoCompras carrinhoComprasBody) throws Exception {

        if(produtoRepository.findById(carrinhoComprasBody.getIdProduto()).isEmpty()){
            System.out.println("erro1");
            throw new ProductNotExistsException();
        }

        if(!carrinhoComprasRepository.findIdByIdClienteAndIdProdutoAndStatus(carrinhoComprasBody.getIdCliente(),carrinhoComprasBody.getIdProduto(),"Aguardando Pagamento").isEmpty()){
            System.out.println("erro2");
            throw new Exception();
        }

        CarrinhoCompras carrinhoCompras = new CarrinhoCompras(carrinhoComprasBody.getIdCliente(), carrinhoComprasBody.getIdProduto(),carrinhoComprasBody.getQuantidade());
        carrinhoComprasRepository.save(carrinhoCompras);
        return new ResponseEntity<>(carrinhoCompras, OK);
    }

    @DeleteMapping("/{idProduto}")
    public ResponseEntity<?> deletar (@PathVariable Long idProduto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).get();

        System.out.println(user.getId());
        System.out.println(idProduto);
        CarrinhoCompras carrinhoCompras = carrinhoComprasRepository.findByIdClienteAndIdProduto(user.getId(),idProduto);

        carrinhoComprasRepository.delete(carrinhoCompras);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/changeQuantidade/{idCarrinho}/{novaQuantidade}")
    public ResponseEntity<CarrinhoCompras> changeQuantidade(@PathVariable(name = "idCarrinho") Long id,@PathVariable(name = "novaQuantidade") String novaQuantidade){
        System.out.println(novaQuantidade + "id: " + id);
        CarrinhoCompras carrinhoCompras = carrinhoComprasRepository.findById(id).get();
        carrinhoCompras.setQuantidade(novaQuantidade);
        carrinhoComprasRepository.save(carrinhoCompras);
        return new ResponseEntity<>(carrinhoCompras,HttpStatus.OK);
    }
}
