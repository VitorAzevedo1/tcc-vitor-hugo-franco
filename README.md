# Sistema de Organização de Informações de Saúde Pessoal na Maturidade

API REST para que pessoas idosas registrem, consultem e compartilhem suas informações de saúde com familiares autorizados, sob consentimento explícito e revogável.

Trabalho de Conclusão de Curso — Engenharia de Computação, Pontifícia Universidade Católica de Goiás.

**Autor:** Vitor Hugo Franco Lopes Azevedo
**Orientador:** Prof. Roussian de Ramos Alves Gaioso

---

## O problema

Informações de saúde de pessoas idosas costumam ficar dispersas entre receituários em papel, anotações soltas e a memória do próprio titular. Isso compromete o acompanhamento dos tratamentos e cria risco concreto em consultas e emergências.

Este sistema centraliza esses registros e permite que a pessoa idosa decida, caso a caso, com quem compartilhá-los — podendo revogar o acesso a qualquer momento.

## Funcionalidades

**Informações de saúde** (operações completas de cadastro, consulta, edição e exclusão)

- Medicamentos em uso, com dosagem, frequência, via de administração e período
- Alergias, classificadas por tipo e gravidade
- Contatos médicos, com especialidade, CRM e local de atendimento
- Histórico de consultas, com diagnóstico e prescrição
- Observações pessoais, classificadas por categoria

**Compartilhamento controlado**

- A pessoa idosa gera um código de convite, válido por prazo determinado e de uso único
- O código é transmitido pelo canal que ela escolher, fora do sistema
- O familiar aceita o convite a partir da própria conta autenticada
- A pessoa idosa revoga o vínculo a qualquer momento, com efeito imediato

**Segurança**

- Senhas armazenadas com hash BCrypt — a senha em texto claro nunca é persistida
- Autenticação sem estado por token JWT assinado
- Isolamento por titularidade: cada usuário acessa apenas os próprios registros
- Diferenciação de permissões por perfil (pessoa idosa / familiar autorizado)

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 (LTS) |
| Framework | Spring Boot 4.1 |
| Segurança | Spring Security + JWT (jjwt) + BCrypt |
| Persistência | Spring Data JPA / Hibernate |
| Banco | MySQL 8.0 |
| Documentação | springdoc-openapi 3.1.1 (Swagger UI) |
| Build | Maven (com Maven Wrapper) |

## Arquitetura

Arquitetura em camadas com organização de pacotes por funcionalidade (*package by feature*). Cada pacote de domínio reúne entidade, repositório, serviço, controlador e objetos de transferência de dados.

```
br.com.pucgo.saudematuridade
├── usuario/            cadastro de contas e perfis de acesso
├── auth/               autenticação, emissão e validação de tokens JWT
├── medicamento/        medicamentos em uso
├── alergia/            alergias, com tipo e gravidade
├── consulta/           histórico de consultas médicas
├── contatomedico/      contatos de profissionais e serviços de saúde
├── observacaopessoal/  observações pessoais por categoria
├── convite/            geração e ciclo de vida dos convites
├── vinculo/            consolidação, consulta e revogação de vínculos
├── familiar/           consultas feitas pelo familiar autorizado
├── exception/          exceções de domínio e tratamento centralizado de erros
└── config/             cadeia de segurança e configuração do OpenAPI
```

O fluxo de cada requisição percorre **Controller → Service → Repository**. As regras de negócio concentram-se na camada de serviço; os controladores não as contêm.

## Como executar

### Pré-requisitos

- JDK 21 ou superior
- MySQL Server 8.0 em execução
- Maven não é necessário: o projeto inclui o Maven Wrapper

### 1. Criar o banco

O banco precisa existir e estar vazio. As tabelas são criadas automaticamente na primeira execução.

```sql
CREATE DATABASE saude_maturidade;
```

### 2. Configurar o acesso

Edite `src/main/resources/application.properties` com as credenciais do seu MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/saude_maturidade?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI
```

> Se a senha do seu MySQL contiver `@`, `:` ou `/`, altere-a ou use codificação percentual — esses caracteres quebram a URL JDBC.

### 3. Executar

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

### 4. Abrir a documentação

```
http://localhost:8080/swagger-ui.html
```

Todos os endpoints podem ser exercitados pela própria interface, sem necessidade de cliente HTTP adicional.

## Usando a API

Todos os recursos são publicados sob o prefixo `/api`.

### Primeiros passos

1. **Cadastre dois usuários** em `POST /api/usuarios` — um com perfil `PESSOA_IDOSA` e outro com `FAMILIAR_AUTORIZADO`.
2. **Autentique-se** em `POST /api/auth/login`. A resposta traz o token.
3. **Autorize as requisições**: no Swagger UI, clique em **Authorize** e cole o token. Em clientes HTTP, envie o cabeçalho `Authorization: Bearer <token>`.

### Principais endpoints

| Método | Caminho | Perfil exigido |
|---|---|---|
| `POST` | `/api/usuarios` | — |
| `POST` | `/api/auth/login` | — |
| `POST` `GET` `PUT` `DELETE` | `/api/medicamentos` | Pessoa idosa |
| `POST` `GET` `PUT` `DELETE` | `/api/alergias` | Pessoa idosa |
| `POST` `GET` `PUT` `DELETE` | `/api/consultas` | Pessoa idosa |
| `POST` `GET` `PUT` `DELETE` | `/api/contatos-medicos` | Pessoa idosa |
| `POST` `GET` `PUT` `DELETE` | `/api/observacoes-pessoais` | Pessoa idosa |
| `POST` | `/api/convites` | Pessoa idosa |
| `POST` | `/api/vinculos/aceitar` | Familiar autorizado |
| `DELETE` | `/api/vinculos/{id}` | Pessoa idosa |

### Códigos de resposta

| Código | Quando ocorre |
|---|---|
| `200` | Consulta, listagem, atualização ou autenticação bem-sucedida |
| `201` | Registro criado, convite gerado ou vínculo consolidado |
| `204` | Exclusão concluída |
| `400` | Violação de regra de negócio, dado inválido ou convite já utilizado |
| `401` | Credenciais inválidas, token ausente, inválido ou expirado |
| `403` | Operação incompatível com o perfil, ou familiar sem vínculo ativo |
| `404` | Registro inexistente, código de convite inválido, ou registro de outro titular |

> O retorno `404` para registros de outro titular é intencional: responder `403` confirmaria que o recurso existe, permitindo inferir a existência de dados alheios.

## Testes

A versão atual foi verificada por **doze casos de teste funcionais**, derivados dos casos de uso especificados — ao menos dois por caso de uso, cobrindo o fluxo principal e os fluxos de exceção. Os resultados estão registrados no Capítulo 5 da monografia.

A execução é manual, via Swagger UI e Postman. A construção de uma suíte automatizada com JUnit e MockMvc está entre os trabalhos futuros.

## Escopo desta versão

**Implementado:** backend completo, com autenticação, as cinco categorias de informações de saúde, o mecanismo de convite e vínculo, a revogação, o isolamento de dados por titular e a documentação automática da API.

**Trabalho futuro:** interface gráfica em React Native, avaliação com usuários reais, testes automatizados, implantação com HTTPS e versionamento de esquema via Flyway, lembretes ativos de medicação e exportação de resumo imprimível.

## Conformidade com a LGPD

Informações de saúde são dados pessoais sensíveis (Lei nº 13.709/2018). O sistema implementa:

- **Consentimento** — nenhum acesso é estabelecido unilateralmente; o familiar precisa aceitar o convite a partir da própria conta
- **Revogação** — a pessoa idosa interrompe o acesso a qualquer momento, com efeito na requisição seguinte
- **Autodeterminação informativa** — exclusão de registros restrita ao titular; o familiar tem acesso somente de leitura
- **Responsabilização** — o vínculo revogado é preservado com as datas de concessão e retirada, mantendo rastreável o consentimento que existiu

## Licença

Trabalho acadêmico desenvolvido para fins de avaliação na Pontifícia Universidade Católica de Goiás.
