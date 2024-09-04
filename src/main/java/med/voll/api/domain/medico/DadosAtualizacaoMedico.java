package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull(message = "Id e obrigatorio")
        Long id,
        String telefone,
        String nome,
        DadosEndereco endereco) {
}
