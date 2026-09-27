package br.com.pucgo.saudematuridade.observacaopessoal;

import br.com.pucgo.saudematuridade.observacaopessoal.dto.ObservacaoPessoalRequest;
import br.com.pucgo.saudematuridade.observacaopessoal.dto.ObservacaoPessoalResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/observacoes")
public class ObservacaoPessoalController {

    private final ObservacaoPessoalService service;

    public ObservacaoPessoalController(ObservacaoPessoalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ObservacaoPessoalResponse> criar(@Valid @RequestBody ObservacaoPessoalRequest request) {
        ObservacaoPessoalResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/observacoes/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ObservacaoPessoalResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservacaoPessoalResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObservacaoPessoalResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ObservacaoPessoalRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
