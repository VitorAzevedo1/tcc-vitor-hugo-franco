package br.com.pucgo.saudematuridade.contatomedico;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContatoMedicoRepository extends JpaRepository<ContatoMedico, Long> {

    List<ContatoMedico> findAllByUsuario(Usuario usuario);

    Optional<ContatoMedico> findByIdAndUsuario(Long id, Usuario usuario);

    boolean existsByIdAndUsuario(Long id, Usuario usuario);
}
