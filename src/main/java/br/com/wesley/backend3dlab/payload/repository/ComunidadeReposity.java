package br.com.wesley.backend3dlab.payload.repository;

import br.com.wesley.backend3dlab.models.Comunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComunidadeReposity extends JpaRepository<Comunidade,Long> {
    Comunidade findByNome(String nome);
}
