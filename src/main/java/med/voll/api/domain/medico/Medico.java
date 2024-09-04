package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private boolean ativo;

    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.crm = dados.crm();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(this.telefone != dados.telefone()) {
            this.telefone = dados.telefone();
        }
        if(this.endereco != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }

    }

    public void excluir() {
        ativo = false;
    }

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }
    public String getCrm() {
        return crm;
    }
    public String getNome() {
        return nome;
    }
}
