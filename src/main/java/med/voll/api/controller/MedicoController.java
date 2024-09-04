package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional

    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBilder){
        var medico = new Medico(dados);
        repository.save(medico);

        //Criando a URI = http://localhost:8080/medicos/{id} (UriComponentsBuilder = devolução do OK 201 + location)
       var uri = uriBilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
       // retorna atraves da URI os dados do dto DaddostalhamentoMedico presentes no id do medico selecionado
       return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
     }
     //Convertendo uma lista de médicos para uma lista de DadosListagemMedico e criando o paginator atraves do Pageable e o Page
     @GetMapping
     // O método Pageable carrega 20 registros do banco de dados sem rodenação (quando não passamos os paremetros na URL da requisição) porém se quisermos trocar esse padrão colocamos a tag @PageableDefault(size=10, page=0, sort={"nome"}) se não colocamos a tag @PageableDefault ele usa os parametros passados na URL se não tiver na URL será utilizado o padrão
     // Para selecionar quantos dados irá me retornar usamos na URL da requisição o ?size= & para selecionar qual página eu quero que o dado seja apresentado usamos page= (size= & page=)
     // Para ordenar por NOME por exemplo usamos ?sort=nome ou para ordenar por qualquer outro atributo usamos ?sort=ATRIBUTO usando o desc para colocar em ordem decrescente ?sort=crm,desc
     public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
         var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
         return ResponseEntity.ok(page);
     }

     @PutMapping
     @Transactional
     public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
         var medico = repository.getReferenceById(dados.id());
         medico.atualizarInformacoes(dados);

         return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
     }
     @DeleteMapping("/{id}")
     @Transactional
     // ResponseEntity é responsável por personalizar os retornos dos métodos de uma classe Controller
     public ResponseEntity excluir(@PathVariable Long id){
         var medico = repository.getReferenceById(id);
         //DEIXA O MEDICO INATIVO PORÉM NÃO EXCLUI DO BANCO DE DADOS (EXCLUSÃO LÓGICA)
         medico.excluir();

        //EXCLUSÃO DE DADOS USANDO O ID
       // repository.deleteById(id);
         return ResponseEntity.noContent().build();
     }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id); // se for lançada um Exception o Spring vai tratar o erro por padrão como Erro500
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}

