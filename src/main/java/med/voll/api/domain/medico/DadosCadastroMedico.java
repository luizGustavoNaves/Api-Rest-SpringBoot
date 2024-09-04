package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        // message faz com que se necessário apareça uma descrição do erro quando o campo for inválido
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        String email,
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "\\d{9}", message = "Telefone deve ter 9 dígitos")
        String telefone,
        @NotBlank(message = "CRM é obrigatório")
        @Pattern(regexp = "\\d{4,6}", message = "CRM deve ter entre 4 e 6 dígitos")
        String crm,
        @NotNull(message = "Especialidade é obrigatório")
        Especialidade especialidade,
        @NotNull(message = "Dados do endereço são obrigatório")
        @Valid
        DadosEndereco endereco) {
}

