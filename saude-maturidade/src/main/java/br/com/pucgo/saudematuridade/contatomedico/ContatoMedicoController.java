package br.com.pucgo.saudematuridade.contatomedico;

import br.com.pucgo.saudematuridade.contatomedico.dto.ContatoMedicoRequest;
import br.com.pucgo.saudematuridade.contatomedico.dto.ContatoMedicoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contatos-medicos")
public class ContatoMedicoController {

    private final ContatoMedicoService service;

    public ContatoMedicoController(ContatoMedicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContatoMedicoResponse> criar(@Valid @RequestBody ContatoMedicoRequest request) {
        ContatoMedicoResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/contatos-medicos/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ContatoMedicoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoMedicoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoMedicoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ContatoMedicoRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}