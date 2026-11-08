package br.com.pucgo.saudematuridade.consulta;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findAllByUsuario(Usuario usuario);

    Optional<Consulta> findByIdAndUsuario(Long id, Usuario usuario);

    boolean existsByIdAndUsuario(Long id, Usuario usuario);
}
