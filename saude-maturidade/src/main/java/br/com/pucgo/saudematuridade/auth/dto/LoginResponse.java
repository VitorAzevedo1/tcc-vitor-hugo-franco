package br.com.pucgo.saudematuridade.auth.dto;

import br.com.pucgo.saudematuridade.usuario.PerfilUsuario;

public record LoginResponse(
        String token,
        Long usuarioId,
        String nome,
        String email,
        PerfilUsuario perfil
) {
}
