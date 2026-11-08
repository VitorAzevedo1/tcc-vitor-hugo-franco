package br.com.pucgo.saudematuridade.vinculo;

import br.com.pucgo.saudematuridade.vinculo.dto.AceitarVinculoRequest;
import br.com.pucgo.saudematuridade.vinculo.dto.VinculoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/vinculos")
@Tag(name = "Vínculos", description = "Aceite e gestão de vínculos entre pessoa idosa e familiar autorizado")
public class VinculoController {

    private final VinculoService service;

    public VinculoController(VinculoService service) {
        this.service = service;
    }

    @PostMapping("/aceitar")
    @Operation(summary = "Familiar autorizado aceita um convite informando o código gerado pela pessoa idosa")
    public ResponseEntity<VinculoResponse> aceitar(@Valid @RequestBody AceitarVinculoRequest request) {
        VinculoResponse response = service.aceitarVinculo(request);
        return ResponseEntity.created(URI.create("/api/vinculos/" + response.id())).body(response);
    }

    @GetMapping("/meus-familiares")
    @Operation(summary = "Pessoa idosa lista os familiares autorizados vinculados a ela")
    public ResponseEntity<List<VinculoResponse>> listarMeusFamiliares() {
        return ResponseEntity.ok(service.listarMeusFamiliares());
    }

    @GetMapping("/meus-idosos")
    @Operation(summary = "Familiar autorizado lista as pessoas idosas às quais está vinculado")
    public ResponseEntity<List<VinculoResponse>> listarMeusIdosos() {
        return ResponseEntity.ok(service.listarMeusIdosos());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Pessoa idosa revoga um vínculo com familiar autorizado")
    public ResponseEntity<Void> revogar(@PathVariable Long id) {
        service.revogar(id);
        return ResponseEntity.noContent().build();
    }
}
