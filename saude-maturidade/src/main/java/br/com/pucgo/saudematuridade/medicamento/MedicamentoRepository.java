package br.com.pucgo.saudematuridade.medicamento;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    List<Medicamento> findAllByUsuario(Usuario usuario);

    Optional<Medicamento> findByIdAndUsuario(Long id, Usuario usuario);

    boolean existsByIdAndUsuario(Long id, Usuario usuario);
}