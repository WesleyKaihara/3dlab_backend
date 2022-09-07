package br.com.wesley.backend3dlab.payload.repository;

import br.com.wesley.backend3dlab.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosReposity extends JpaRepository<Produto,Long> {
    Produto findByNome(String nome);
}
