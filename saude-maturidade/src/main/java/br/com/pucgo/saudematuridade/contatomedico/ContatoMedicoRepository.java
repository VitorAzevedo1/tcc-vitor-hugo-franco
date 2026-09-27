package br.com.pucgo.saudematuridade.contatomedico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoMedicoRepository extends JpaRepository<ContatoMedico, Long> {
}
