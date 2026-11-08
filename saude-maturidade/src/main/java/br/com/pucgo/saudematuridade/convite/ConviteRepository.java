package br.com.pucgo.saudematuridade.convite;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConviteRepository extends JpaRepository<Convite, Long> {

    Optional<Convite> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Convite> findAllByIdosoOrderByDataGeracaoDesc(Usuario idoso);
}
