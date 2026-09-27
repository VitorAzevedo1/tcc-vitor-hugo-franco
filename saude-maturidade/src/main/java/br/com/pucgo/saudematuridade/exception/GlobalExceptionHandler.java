package br.com.pucgo.saudematuridade.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request
    ) {
        ErroResposta erro = ErroResposta.simples(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResposta> tratarRegraDeNegocio(
            RegraDeNegocioException ex,
            HttpServletRequest request
    ) {
        ErroResposta erro = ErroResposta.simples(
                HttpStatus.BAD_REQUEST.value(),
                "Regra de negócio violada",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarValidacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ErroResposta.CampoInvalido> camposInvalidos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapearCampoInvalido)
                .toList();

        ErroResposta erro = ErroResposta.comCampos(
                HttpStatus.BAD_REQUEST.value(),
                "Dados inválidos",
                "Um ou mais campos estão inválidos",
                request.getRequestURI(),
                camposInvalidos
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarErroGenerico(
            Exception ex,
            HttpServletRequest request
    ) {
        ErroResposta erro = ErroResposta.simples(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                "Ocorreu um erro inesperado. Tente novamente ou contate o suporte.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    private ErroResposta.CampoInvalido mapearCampoInvalido(FieldError fieldError) {
        return new ErroResposta.CampoInvalido(fieldError.getField(), fieldError.getDefaultMessage());
    }
}