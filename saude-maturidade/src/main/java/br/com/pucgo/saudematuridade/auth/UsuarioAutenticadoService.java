package br.com.pucgo.saudematuridade.auth;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import br.com.pucgo.saudematuridade.usuario.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAutenticadoService {

    private final UsuarioRepository repository;

    public UsuarioAutenticadoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Nenhum usuário autenticado no contexto");
        }

        String email = authentication.getName();

        return repository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuário autenticado não encontrado: " + email));
    }
}