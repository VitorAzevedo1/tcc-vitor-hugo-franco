package br.com.pucgo.saudematuridade.consulta;

import br.com.pucgo.saudematuridade.consulta.dto.ConsultaRequest;
import br.com.pucgo.saudematuridade.consulta.dto.ConsultaResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponse> criar(@Valid @RequestBody ConsultaRequest request) {
        ConsultaResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/consultas/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConsultaRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}