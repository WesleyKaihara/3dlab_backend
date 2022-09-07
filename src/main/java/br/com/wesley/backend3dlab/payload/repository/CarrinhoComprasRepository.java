package br.com.wesley.backend3dlab.payload.repository;

import br.com.wesley.backend3dlab.models.CarrinhoCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrinhoComprasRepository extends JpaRepository<CarrinhoCompras,Long> {

    List<Long> findIdByIdClienteAndIdProdutoAndStatus(Long idCliente,Long idProduto,String status);

    CarrinhoCompras findByIdClienteAndIdProduto(Long idCliente,Long idProduto);

    Iterable<CarrinhoCompras> findAllByIdCliente(Long idCliente);
}
