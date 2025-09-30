README


#  Projeto PyLadies Indicações

Este projeto tem como objetivo cadastrar e divulgar profissionais e empreendimentos liderados por mulheres, promovendo visibilidade e conexão com a comunidade.

---

## Requisitos Funcionais (RF)

O sistema deve permitir:

- Cadastrar profissionais e empreendimentos com os seguintes campos:
  - Nome completo
  - Área de atuação
  - Contatos: telefone, email
  - Outras informações relevantes  
  *(Função: `salvar`)*

- Remover cadastros duplicados ou incorretos  
- Editar dados de profissionais  
- Visualizar todos os profissionais cadastrados  
- Listar áreas cadastradas para padronização  
- Buscar profissionais por área  
- Buscar profissionais por ID  

---

## Requisitos Não-Funcionais (RNF)

### Performance
- Resposta rápida para buscas e cadastros  


###  Usabilidade
- Interface simples, clara e consistente  
- Compatível com diferentes navegadores e tamanhos de tela  

### Tecnologias Utilizadas
- **Backend:** Java 21 + Spring Boot WebFlux  
- **Banco de dados:** MongoDB  
- **Testes:** JUnit  

---

## Papéis da Equipe

| Nome             | Função                                         |
|------------------|------------------------------------------------|
| Mikaele Santos   | Backend                                        |
| Henrique         | Testes                                         |
| Pedro Atila      | Documentação                                   |
| Thales           | Validação de testes e requisitos               |
| Clara            | Contato com a comunidade e intermediação       |

---

## Requisitos de Programação Funcional

Este projeto aplica conceitos de programação funcional conforme exigido:

### Função Lambda
Presente em vários pontos, como no método `buscarPorId`:

```java
.flatMap(p -> ServerResponse.ok().bodyValue(p))
```

###  List Comprehension (equivalente em Java)
Utilizado no método `listarAreas` com Stream API:

```java
.map(list ->
    list.stream()
        .map(Profissional::area)
        .distinct()
        .toList()
)
```

###  Closure
Presente na função `editar`, onde a lambda captura o contexto externo (`novosDados`):

```java
Function<Profissional, Profissional> criarNovoRegistro = p ->
    new Profissional(
        null,
        novosDados.nome() != null ? novosDados.nome() : p.nome(),
        novosDados.area() != null ? novosDados.area() : p.area(),
        novosDados.contato() != null ? novosDados.contato() : p.contato(),
        novosDados.camposEspecificos() != null ? novosDados.camposEspecificos() : p.camposEspecificos()
    );
```

### Função de Alta Ordem
A função `editar` utiliza uma função de alta ordem ao definir `criarNovoRegistro` como uma `Function<Profissional, Profissional>`.

## Como Executar o Projeto

### Pré-requisitos

- Java 21 instalado
- MongoDB em execução local ou remoto
- Node.js + npm (para o frontend)
- Variáveis de ambiente configuradas para as APIs externas (Google e Meta)

1. Acesse a pasta do backend:

2. Compile e execute:
   ```bash
   ./mvnw spring-boot:run
   ```

3. O backend estará disponível em:
   ```
   http://localhost:8080
   ```
