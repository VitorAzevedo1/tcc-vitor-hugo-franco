package br.com.pucgo.saudematuridade.observacaopessoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservacaoPessoalRepository extends JpaRepository<ObservacaoPessoal, Long> {
}