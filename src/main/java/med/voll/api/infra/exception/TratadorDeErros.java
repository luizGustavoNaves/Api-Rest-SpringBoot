package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // classe expecífica que trata erros
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErros404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErros400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DdaosErroValidacao::new).toList());
    }

    private record DdaosErroValidacao(String campo, String mensagem) {
        public DdaosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
            // cria-se uma lista com os erros e uma mensagem descritiva
        }
    }
}
