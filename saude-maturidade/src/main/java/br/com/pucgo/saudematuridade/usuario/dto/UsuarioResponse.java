package br.com.pucgo.saudematuridade.usuario.dto;

import br.com.pucgo.saudematuridade.usuario.PerfilUsuario;
import br.com.pucgo.saudematuridade.usuario.Usuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        PerfilUsuario perfil,
        LocalDateTime dataCadastro
) {

    public static UsuarioResponse fromEntity(Usuario u) {
        return new UsuarioResponse(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getPerfil(),
                u.getDataCadastro()
        );
    }
}
