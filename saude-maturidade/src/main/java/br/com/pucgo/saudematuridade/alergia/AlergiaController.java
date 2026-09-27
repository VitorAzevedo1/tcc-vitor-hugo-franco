package br.com.pucgo.saudematuridade.alergia;

import br.com.pucgo.saudematuridade.alergia.dto.AlergiaRequest;
import br.com.pucgo.saudematuridade.alergia.dto.AlergiaResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/alergias")
public class AlergiaController {

    private final AlergiaService service;

    public AlergiaController(AlergiaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AlergiaResponse> criar(@Valid @RequestBody AlergiaRequest request) {
        AlergiaResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/alergias/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AlergiaResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlergiaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlergiaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AlergiaRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
