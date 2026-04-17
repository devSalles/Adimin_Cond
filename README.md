# 🏢 Sistema de Gerenciamento de Condomínio

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203.0-85EA2D?style=flat-square)

API REST desenvolvida com **Spring Boot** para gerenciar moradores, apartamentos, veículos, visitantes e taxas de condomínio, com regras de negócio robustas e exclusão lógica em todas as entidades.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Descrição |
|---|---|
| **Spring Boot** | Framework principal para construção da API REST |
| **MySQL** | Banco de dados relacional para persistência |
| **Swagger / OpenAPI 3.0** | Documentação interativa da API |
| **Spring Data JPA** | Abstração de acesso a dados |
| **Bean Validation** | Validação declarativa de campos obrigatórios |

---

## 📡 Endpoints da API

### 🧑‍💼 Morador — `/morador`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/morador/adicionar` | Cadastra um novo morador |
| `PUT` | `/morador/atualizar-morador/{id}` | Atualiza os dados de um morador |
| `GET` | `/morador/listar-todos` | Lista todos os moradores |
| `GET` | `/morador/buscar-id/{id}` | Busca morador por ID |
| `GET` | `/morador/buscar-nome/{nome}` | Busca morador por nome |
| `GET` | `/morador/buscar-por-status/{status}` | Busca moradores por status (`ATIVO` / `INATIVO`) |
| `GET` | `/morador/buscar-CPF/{cpf}` | Busca morador por CPF |
| `GET` | `/morador/buscar-email/{email}` | Busca morador por e-mail |
| `DELETE` | `/morador/deletar-morador/{id}` | Inativa um morador (exclusão lógica) |

#### Exemplo de requisição — `POST /morador/adicionar`

```json
{
  "nome": "Carlos Henrique Souza",
  "cpf": "52998224725",
  "email": "carlos.souza@email.com",
  "telefone": "(32)95461-9842"
}
```

### 🏢 Apartamento — `/apartamento`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/apartamento/salvar-apartamento` | Cadastra um novo apartamento |
| `POST` | `/apartamento/vinvular-apartamento/{idApartamento}/{idMorador}` | Vincula um morador a um apartamento |
| `POST` | `/apartamento/desvincular-apartamento/{id}` | Desvincula o morador do apartamento |
| `PUT` | `/apartamento/atualizarApto-id/{id}` | Atualiza os dados de um apartamento |
| `PUT` | `/apartamento/colocar-manutencao/{id}` | Coloca o apartamento em manutenção |
| `PUT` | `/apartamento/retirar-manutencao/{id}` | Retira o apartamento da manutenção |
| `GET` | `/apartamento/listar-todos` | Lista todos os apartamentos |
| `GET` | `/apartamento/buscar-id/{id}` | Busca apartamento por ID |
| `GET` | `/apartamento/buscar-status-apartamento/{statusApartamento}` | Busca por status (`OCUPADO` / `DESOCUPADO` / `MANUTENCAO`) |
| `GET` | `/apartamento/buscar-bloco/{blocoApt}` | Busca apartamentos por bloco |
| `DELETE` | `/apartamento/desativar-apartamento/{id}` | Desativa um apartamento (exclusão lógica) |

#### Exemplo de requisição — `POST /apartamento/salvar-apartamento`

```json
{
  "numero": 101,
  "bloco": "A",
  "andar": 1,
  "observacoes": "Apartamento próximo ao elevador"
}
```

### 🚗 Veículo — `/veiculo`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/veiculo/adicionar` | Cadastra um novo veículo |
| `PUT` | `/veiculo/atualizar/{id}` | Atualiza os dados de um veículo |
| `GET` | `/veiculo/buscar-placa/{placa}` | Busca veículo por placa |
| `GET` | `/veiculo/listar-todos` | Lista todos os veículos |
| `GET` | `/veiculo/buscar/{id}` | Busca veículo por ID |
| `GET` | `/veiculo/listar-por-status/{status}` | Busca veículos por status (`ATIVO` / `INATIVO`) |
| `DELETE` | `/veiculo/desativar/{id}` | Desativa um veículo (exclusão lógica) |

#### Exemplo de requisição — `POST /veiculo/adicionar`

```json
{
  "placa": "GAC-0043",
  "modelo": "Honda Civic",
  "cor": "Prata",
  "idMorador": 1
}
```
### 🚶 Visitante — `/visitante`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/visitante/entrada` | Registra a entrada de um visitante |
| `POST` | `/visitante/saida` | Registra a saída de um visitante |
| `GET` | `/visitante/listar-registros` | Lista todos os registros de visitantes |
| `GET` | `/visitante/buscar-cpf/{cpf}` | Busca visitante por CPF |
| `GET` | `/visitante/buscar-nome/{nome}` | Busca visitantes por nome |
| `GET` | `/visitante/buscar/{id}` | Busca registro de visita por ID |
| `GET` | `/visitante/buscar-status/{statusVisitante}` | Busca por status de visita |
| `GET` | `/visitante/pesquisar-por-data-entrada` | Filtra visitas por período de entrada (`?inicio=&fim=`) |
| `GET` | `/visitante/pesquisar-por-data-saida` | Filtra visitas por período de saída (`?inicio=&fim=`) |

#### Exemplo de requisição — `POST /visitante/entrada`

```json
{
  "nome": "Ana Beatriz Souza",
  "cpf": "11144477735",
  "idMorador": 1
}
```

#### Exemplo de requisição — `POST /visitante/saida`

```json
{
  "cpf": "11144477735"
}
```

### 💰 Taxa de Condomínio — `/taxas`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/taxas/adicionar-taxa` | Gera uma nova taxa de condomínio |
| `PATCH` | `/taxas/pagar-taxa/{id}` | Registra o pagamento de uma taxa (`?dataPagamento=`) |
| `GET` | `/taxas/buscar/{id}` | Busca taxa por ID |
| `GET` | `/taxas/listar-todos` | Lista todas as taxas |
| `GET` | `/taxas/data-pagamento` | Filtra taxas por período de pagamento (`?inicio=&fim=`) |
| `GET` | `/taxas/data-vencimento` | Filtra taxas por período de vencimento (`?inicio=&fim=`) |

#### Exemplo de requisição — `POST /taxas/adicionar-taxa`

```json
{
  "referencia": "Abril/2026",
  "valor": 350.75,
  "dataVencimento": "2027-10-17",
  "idMorador": 1
}
```

### 🔑 Acesso — `/acesso`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/acesso/entrada` | Registra entrada de veículo |
| `POST` | `/acesso/saida` | Registra saída de veículo |
| `GET` | `/acesso/listagem` | Lista todos os registros de acesso |
| `GET` | `/acesso/buscar/{id}` | Busca registro de acesso por ID |
| `GET` | `/acesso/consultar-por-data-entrada` | Filtra acessos por período de entrada (`?inicio=&fim=`) |
| `GET` | `/acesso/consultar-por-data-saida` | Filtra acessos por período de saída (`?inicio=&fim=`) |
| `GET` | `/acesso/consultar-por-status` | Filtra acessos por tipo (`?tipoAcesso=`) |
| `GET` | `/acesso/consultar-id-veiculo/{idVeiculo}` | Lista acessos de um veículo específico |

---

## 📐 Regras de Negócio

### 🧑‍💼 Morador

- Deve possuir nome, CPF válido, telefone e e-mail.
- **CPF e e-mail são únicos** no sistema.
- Status possíveis: `ATIVO` | `INATIVO`
- Vinculado a **no máximo um** apartamento.
- Pode possuir vários veículos, visitantes e taxas.
- Morador `INATIVO`:
  - Não pode receber novas taxas.
  - Não pode cadastrar veículos.
  - Não pode registrar visitantes.
- Só pode ser inativado se **não houver visitas em andamento**.

---

### 🏢 Apartamento

- Deve possuir número, bloco e andar.
- Status possíveis: `OCUPADO` | `DESOCUPADO` | `MANUTENCAO`
- Aceita **no máximo um morador**.
- Regras por status:
  - `DESOCUPADO`: não pode possuir morador vinculado.
  - `OCUPADO`: deve obrigatoriamente possuir um morador.
  - `MANUTENCAO`: não pode receber novo morador.
- Um morador **não pode ocupar mais de um apartamento** simultaneamente.
- O status é **atualizado automaticamente** ao vincular ou remover morador.

---

### 🚗 Veículo

- Deve possuir **placa válida e única**, modelo e cor.
- A placa é **armazenada sem formatação**.
- Status possíveis: `ATIVO` | `INATIVO`
- Deve estar vinculado a um morador `ATIVO`.
- Veículo `INATIVO`:
  - Não pode ser utilizado no controle de acesso.
  - Não pode participar de novas operações.

---

### 🚶 Visitante

- Deve possuir nome e documento.
- Deve estar vinculado a um morador `ATIVO`.
- A visita contém data/hora de entrada obrigatória.
- A data/hora de saída **não pode ser anterior** à de entrada.
- Um visitante **não pode ter mais de uma visita ativa** simultaneamente.
- A visita só é considerada finalizada após o registro da saída.
- Não é permitido finalizar uma visita **já encerrada**.

---

### 💰 Taxa de Condomínio

- Deve possuir referência (mês/ano), valor e data de vencimento.
- Status possíveis: `PENDENTE` | `PAGA` | `ATRASADA`
- Só pode ser gerada para morador `ATIVO`.
- **Não pode existir mais de uma taxa** com a mesma referência para o mesmo morador.
- Toda taxa inicia com status `PENDENTE`.
- Ao ultrapassar o vencimento sem pagamento → status muda automaticamente para `ATRASADA`.
- Taxa `PAGA`:
  - Não pode ser alterada.
  - Não pode ser excluída logicamente.
  - Não é permitido registrar pagamento novamente.

---

## ⚙️ Regras Gerais

- Nenhum campo obrigatório pode ser **nulo ou vazio**.
- Todas as exclusões utilizam **exclusão lógica** (soft delete) — nenhum registro é deletado fisicamente.
- Todas as operações críticas são **transacionais**.
- Erros retornam mensagens claras, por exemplo:
  - `"Morador inativo"`
  - `"Apartamento em manutenção"`
  - `"Taxa duplicada para o mesmo período"`

---

## 🔄 Atualizações Automáticas

| Evento | Ação automática |
|---|---|
| Morador vinculado a apartamento | Status do apartamento → `OCUPADO` |
| Morador removido do apartamento | Status do apartamento → `DESOCUPADO` |
| Taxa vence sem pagamento | Status da taxa → `ATRASADA` |
| Pagamento registrado | Status da taxa → `PAGA` |
| Saída do visitante registrada | Visita marcada como finalizada |

---

## 📄 Documentação da API

Após subir a aplicação, acesse a documentação interativa via Swagger:

```
http://localhost:8080/swagger-ui.html
```

---

## 🚀 Como Executar

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/seu-repositorio.git

# Configure o banco de dados em application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/condominio
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# Execute a aplicação
./mvnw spring-boot:run
```

---

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/condominio/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── model/
│   │       ├── dto/
│   │       └── exception/
│   └── resources/
│       └── application.properties
```

---
