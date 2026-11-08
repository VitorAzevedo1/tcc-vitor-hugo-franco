package br.com.pucgo.saudematuridade.usuario;

import br.com.pucgo.saudematuridade.usuario.dto.CadastroUsuarioRequest;
import br.com.pucgo.saudematuridade.usuario.dto.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Cadastro e gerenciamento de usuários do sistema")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/registro")
    @Operation(summary = "Cadastra um novo usuário", description = "Registra um novo usuário no sistema, com perfil de pessoa idosa ou familiar autorizado. A senha é armazenada com hash BCrypt.")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody CadastroUsuarioRequest request) {
        UsuarioResponse response = service.cadastrar(request);
        return ResponseEntity.created(URI.create("/api/usuarios/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
