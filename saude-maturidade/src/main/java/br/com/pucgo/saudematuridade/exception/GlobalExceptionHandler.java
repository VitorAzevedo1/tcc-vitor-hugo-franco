package br.com.pucgo.saudematuridade.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "br.com.pucgo.saudematuridade")
public class GlobalExceptionHandler {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> tratarJsonInvalido(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        String mensagem = "Corpo da requisição inválido ou mal formatado";

        // Se o erro for de conversão de enum, tenta dar uma mensagem mais útil
        Throwable causa = ex.getCause();
        if (causa instanceof InvalidFormatException formatEx) {
            Class<?> tipoAlvo = formatEx.getTargetType();
            if (tipoAlvo != null && tipoAlvo.isEnum()) {
                String campo = formatEx.getPath().isEmpty()
                        ? "campo"
                        : formatEx.getPath().get(formatEx.getPath().size() - 1).getFieldName();
                String valorRecebido = String.valueOf(formatEx.getValue());
                String valoresAceitos = Arrays.stream(tipoAlvo.getEnumConstants())
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
                mensagem = String.format(
                        "Valor inválido para o campo '%s': '%s'. Valores aceitos: %s",
                        campo, valorRecebido, valoresAceitos
                );
            }
        }

        ErroResposta erro = ErroResposta.simples(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida",
                mensagem,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResposta> tratarParametroInvalido(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String mensagem = String.format(
                "O parâmetro '%s' recebeu um valor inválido: '%s'",
                ex.getName(),
                ex.getValue()
        );
        ErroResposta erro = ErroResposta.simples(
                HttpStatus.BAD_REQUEST.value(),
                "Parâmetro inválido",
                mensagem,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarErroGenerico(
            Exception ex,
            HttpServletRequest request
    )
    {
        logger.error("Erro não tratado em {}: {}", request.getRequestURI(), ex.getMessage(), ex);
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