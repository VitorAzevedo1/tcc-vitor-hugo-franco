package br.com.pucgo.saudematuridade.medicamento;

import br.com.pucgo.saudematuridade.medicamento.dto.MedicamentoRequest;
import br.com.pucgo.saudematuridade.medicamento.dto.MedicamentoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final MedicamentoService service;

    public MedicamentoController(MedicamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MedicamentoResponse> criar(@Valid @RequestBody MedicamentoRequest request) {
        MedicamentoResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/medicamentos/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MedicamentoRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
