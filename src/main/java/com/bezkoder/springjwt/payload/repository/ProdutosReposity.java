package com.bezkoder.springjwt.payload.repository;

import com.bezkoder.springjwt.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosReposity extends JpaRepository<Produto,Long> {
    Produto findByNome(String nome);
}
