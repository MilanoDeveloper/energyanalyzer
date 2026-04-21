package br.com.fiap.energyanalyzer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> build(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ApiError(status.getReasonPhrase(), message));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() == LocalDate.class) {
            String msg = "Data inválida no parâmetro '" + ex.getName() + "'. " +
                    "Use o formato AAAA-MM-DD com uma data real. Valor recebido: " + ex.getValue();
            return build(HttpStatus.BAD_REQUEST, msg);
        }
        return build(HttpStatus.BAD_REQUEST,
                "Parâmetro inválido: " + ex.getName() + " = " + ex.getValue());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParam(MissingServletRequestParameterException ex) {
        String msg = "Parâmetro obrigatório ausente: '" + ex.getParameterName() + "'.";
        return build(HttpStatus.BAD_REQUEST, msg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleNotReadable(HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST,
                "Corpo da requisição inválido");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        return build(HttpStatus.BAD_REQUEST,
                "Dados inválidos. Verifique os campos obrigatórios e formatos.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArg(IllegalArgumentException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro inesperado. Se persistir, contate o suporte.");
    }
}
