package br.com.pucgo.saudematuridade.convite;

import br.com.pucgo.saudematuridade.convite.dto.ConviteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/convites")
@Tag(name = "Convites", description = "Geração de códigos de convite pela pessoa idosa")
public class ConviteController {

    private final ConviteService service;

    public ConviteController(ConviteService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Gera um novo código de convite", description = "Apenas pessoa idosa autenticada pode gerar um código. O código tem validade de 7 dias.")
    public ResponseEntity<ConviteResponse> gerar() {
        ConviteResponse response = service.gerarCodigo();
        return ResponseEntity.created(URI.create("/api/convites/" + response.id())).body(response);
    }

    @GetMapping
    @Operation(summary = "Lista os convites gerados pela pessoa idosa autenticada")
    public ResponseEntity<List<ConviteResponse>> listar() {
        return ResponseEntity.ok(service.listarMeusConvites());
    }
}