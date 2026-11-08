package br.com.pucgo.saudematuridade.vinculo;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VinculoRepository extends JpaRepository<Vinculo, Long> {

    List<Vinculo> findAllByIdosoAndStatus(Usuario idoso, VinculoStatus status);

    List<Vinculo> findAllByFamiliarAndStatus(Usuario familiar, VinculoStatus status);

    Optional<Vinculo> findByIdAndIdoso(Long id, Usuario idoso);

    boolean existsByIdosoAndFamiliarAndStatus(Usuario idoso, Usuario familiar, VinculoStatus status);

    Optional<Vinculo> findByIdosoAndFamiliarAndStatus(Usuario idoso, Usuario familiar, VinculoStatus status);
}
