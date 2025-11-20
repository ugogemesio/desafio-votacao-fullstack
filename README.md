# Desafio Votação – Sistema de Gerenciamento de Votos

Sistema de votação online para cooperativas, permitindo que associados cadastrem pautas, participem de sessões de votação e visualizem resultados de forma segura e persistente.

**Demo:** [Aplicação em nuvem](https://desafio-votacao-fullstack-front-v03.vercel.app)

**Backend:** Java 17 + Spring Boot 3.5.7  
**Frontend:** React 19 + TypeScript 5.9

---

## Índice

1. [Objetivo](#objetivo)
2. [Funcionalidades](#funcionalidades)
3. [Tecnologias](#tecnologias)
4. [Arquitetura do Sistema](#arquitetura-do-sistema1.0)
5. [Branches](#arquitetura-do-sistema1.1)
6. [Fluxo de Uso](#fluxo-de-uso)
7. [Execução do Projeto](#execucao-do-projeto)
8. [Decisões Técnicas Relevantes](#decisoes-tecnicas-relevantes)
9. [Testes & Performance](#testes--performance)
10. [Limitações Conhecidas](#limitacoes-conhecidas)
11. [Contribuição](#contribuicao)

---

## Objetivo

Criar uma solução para gerenciar assembleias de cooperativas, com funcionalidades principais:

- Cadastro de pautas.
- Abertura de sessões de votação (tempo configurável, default 1 min).
- Registro de votos Sim/Não, garantindo 1 voto por associado por pauta.
- Cálculo automático de resultados.
- Persistência de dados mesmo após reinicialização da aplicação.

---

## Funcionalidades

### Principais

- Cadastro de pautas.
- Abertura de sessões de votação com tempo configurável.
- Registro de votos Sim/Não por associado.
- Contabilização automática dos votos e exibição do resultado da pauta.
- Persistência de dados (PostgreSQL) para manter informações após reinício da aplicação.

### Bônus

1. **Integração com sistemas externos**
   - Fake client para validação de CPF.
   - Retorna aleatoriamente `ABLE_TO_VOTE` ou `UNABLE_TO_VOTE`.
   - CPF inválido retorna 404.
   - Caso exista o cpf digitado em banco, usuario é direcionado para
   -  aplicação e caso contrário(não salvo ainda no banco), se chamada
   -  randomica 50/50 voltar ABLE_TO_VOTE, usuário preenche nome e utilizar o sistema

2. **Performance**
   - Testes de performance e integração com Redis.
   - Cenário: 100.000 votos computados em ~11 segundos.

3. **Versionamento de API**
   - Estratégia via URL: `/api/v1/...`

---

## Tecnologias

**Backend:** Spring Boot, PostgreSQL, Redis, Hibernate, MapStruct, Lombok, Flyway, Spring Validation, JUnit 5, Testcontainers, Swagger.

**Frontend:** React, TypeScript, TailwindCSS, Sass, React Query, React Router DOM, Axios, Vite.

---

## Arquitetura do Sistema

### Conceito

```
+------------------+        +----------------+                          +----------------+
|     Frontend     | <-dto> |    Backend     | <-informações e dados-> | PostgreSQL/Redis|
|  React + TS      |        | Spring Boot    |                         | Persistência    |
| - Pages/Services |        | - Controllers  |                         | H2 PARA TESTE   |
| - Hooks / Types  |        | - Services     |                         |                 |
+------------------+        | - Repositories |                          +----------------+
                            | - DTOs/Mapper  |
                            | - Scheduler/Events |
                             +----------------+
```

- **Backend**: Controllers → Services → Repositories; DTOs e Mappers isolam entidades; Eventos e Scheduler automatizam encerramento de sessões.
- **Frontend**: Estrutura por features (`associado`, `pauta`, `sessao`, `voto`, `resultado`), com hooks, services, pages e componentes reutilizáveis.


## Branches

O repositório está sendo organizado em três branches principais:

- **`dev`** – Branch de desenvolvimento  (atual v0.1)
  Todas as novas funcionalidades e ajustes são feitos aqui. Testes e experimentos iniciais ocorrem nesta branch.

- **`teste`** – Branch de testes e integração  (atual v0.8)
  Recebe merges da `dev` quando as funcionalidades estão estáveis. Usada para validação de integração e testes de performance. Não deve conter alterações instáveis.

- **`deploy`** – Branch de produção / deployment  (migrar para main )
  Contém a versão final e estável do sistema. Usada para deploy em nuvem e demonstração. Apenas merges da branch `teste` são permitidos.

**Fluxo recomendado:**  
`dev` → `teste` → `deploy`

---
## Branches

O repositório está sendo organizado em três branches principais, cada uma com um propósito específico. Atualmente, a nomenclatura e commits ainda estão em transição:

- **`dev`** – Branch de desenvolvimento (atualmente v0.1)  
  Todas as novas funcionalidades e ajustes são feitos aqui. Testes e experimentos iniciais ocorrem nesta branch.

- **`teste`** – Branch de testes e integração (atualmente v0.8)  
  Recebe merges da `dev` quando as funcionalidades estão estáveis. Usada para validação de integração e testes de performance. Não deve conter alterações instáveis.

- **`deploy`** – Branch de produção / deployment (planejada para migrar para `main`)  
  Contém a versão final e estável do sistema. Usada para deploy em nuvem e demonstração. Recebe merges da branch `teste` .

>  O fluxo ideal é `dev` → `teste` → `deploy`. A ideia era/é alinhar a nomenclatura das branches com o padrão final após estabilização do projeto.

## Fluxo de Uso

```
Login via CPF(fake client)
      |
      v
  Criar Pauta
      |
      v
Abrir Sessão (tempo configurável)
      |
      v
Associados votam
      |
      v
Encerramento da Sessão (Scheduler)
      |
      v
Cálculo e armazenamento do Resultado
```

- Exclusão de pautas não implementada para simular auditoria (soft delete).

---

## Execução do Projeto

**Pré-requisitos:** Java 17, Node.js + npm/yarn, PostgreSQL, Redis (opcional), Gradle

### Backend
```bash
cd backend
./gradlew clean build
./gradlew bootRun
(abaixo se quiser testar)
./gradlew test 
```


### Frontend
```bash
cd frontend
npm install
npm run dev
```


### Acesso
- Swagger: `http://localhost:8080/api/v1/swagger-ui.html`
- Frontend: `http://localhost:5173`

---

## Decisões Técnicas Relevantes

- **DTOs + MapStruct**: Evitam exposição direta das entidades e simplificam serialização.
- **Redis**: Cache e performance para grandes volumes de votos.
- **React Query**: Gerenciamento eficiente de cache e requisições HTTP.
- **Eventos e Scheduler**: Encerramento de sessões desacoplado do fluxo principal.
- **Soft Delete**: Preserva histórico de votações.

---

## Testes & Performance

- **Integração**: Testcontainers (PostgreSQL).
- **Performance**: Cenário de 100.000 votos processados em ~11s com Redis.

---

## Limitações Conhecidas

- Servidores gratuitos podem entrar em idle, afetando integridade temporariamente.
- Segurança
- Para fins do desafio, todas as chamadas à API são consideradas autorizadas.
-  Não há autenticação ou controle de acesso implementados, já que a segurança pode ser abstraída conforme orientação do teste.



---

## Contribuição

1. Faça **fork** do repositório.
2. Implemente alterações.
3. Crie **Pull Request** para análise.
