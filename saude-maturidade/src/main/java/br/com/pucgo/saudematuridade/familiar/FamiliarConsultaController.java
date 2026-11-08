package br.com.pucgo.saudematuridade.familiar;

import br.com.pucgo.saudematuridade.alergia.dto.AlergiaResponse;
import br.com.pucgo.saudematuridade.consulta.dto.ConsultaResponse;
import br.com.pucgo.saudematuridade.contatomedico.dto.ContatoMedicoResponse;
import br.com.pucgo.saudematuridade.medicamento.dto.MedicamentoResponse;
import br.com.pucgo.saudematuridade.observacaopessoal.dto.ObservacaoPessoalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/familiar/idosos/{idosoId}")
@Tag(name = "Familiar — Consulta", description = "Consulta somente leitura dos dados de saúde da pessoa idosa vinculada")
public class FamiliarConsultaController {

    private final AcessoFamiliarService service;

    public FamiliarConsultaController(AcessoFamiliarService service) {
        this.service = service;
    }

    @GetMapping("/medicamentos")
    @Operation(summary = "Lista os medicamentos da pessoa idosa vinculada")
    public ResponseEntity<List<MedicamentoResponse>> listarMedicamentos(@PathVariable Long idosoId) {
        return ResponseEntity.ok(service.listarMedicamentos(idosoId));
    }

    @GetMapping("/alergias")
    @Operation(summary = "Lista as alergias da pessoa idosa vinculada")
    public ResponseEntity<List<AlergiaResponse>> listarAlergias(@PathVariable Long idosoId) {
        return ResponseEntity.ok(service.listarAlergias(idosoId));
    }

    @GetMapping("/contatos-medicos")
    @Operation(summary = "Lista os contatos médicos da pessoa idosa vinculada")
    public ResponseEntity<List<ContatoMedicoResponse>> listarContatosMedicos(@PathVariable Long idosoId) {
        return ResponseEntity.ok(service.listarContatosMedicos(idosoId));
    }

    @GetMapping("/consultas")
    @Operation(summary = "Lista o histórico de consultas da pessoa idosa vinculada")
    public ResponseEntity<List<ConsultaResponse>> listarConsultas(@PathVariable Long idosoId) {
        return ResponseEntity.ok(service.listarConsultas(idosoId));
    }

    @GetMapping("/observacoes")
    @Operation(summary = "Lista as observações pessoais da pessoa idosa vinculada")
    public ResponseEntity<List<ObservacaoPessoalResponse>> listarObservacoes(@PathVariable Long idosoId) {
        return ResponseEntity.ok(service.listarObservacoes(idosoId));
    }
}