package br.com.pucgo.saudematuridade.observacaopessoal;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObservacaoPessoalRepository extends JpaRepository<ObservacaoPessoal, Long> {

    List<ObservacaoPessoal> findAllByUsuario(Usuario usuario);

    Optional<ObservacaoPessoal> findByIdAndUsuario(Long id, Usuario usuario);

    boolean existsByIdAndUsuario(Long id, Usuario usuario);
}