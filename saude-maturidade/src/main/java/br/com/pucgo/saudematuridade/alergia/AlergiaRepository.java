package br.com.pucgo.saudematuridade.alergia;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlergiaRepository extends JpaRepository<Alergia, Long> {

    List<Alergia> findAllByUsuario(Usuario usuario);

    Optional<Alergia> findByIdAndUsuario(Long id, Usuario usuario);

    boolean existsByIdAndUsuario(Long id, Usuario usuario);
}