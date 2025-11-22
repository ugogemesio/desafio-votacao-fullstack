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
8. [Documentação da API (Swagger)](#documentacao-swagger)
9. [Decisões Técnicas Relevantes](#decisoes-tecnicas-relevantes)
10. [Testes & Performance](#testes--performance)
11. [Limitações Conhecidas](#limitacoes-conhecidas)
12. [Contribuição](#contribuicao)

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

Tela de login simulada com localstorage e implementando tarefa bônus; 

<img width="1841" height="1501" alt="image" src="https://github.com/user-attachments/assets/8da24575-aec7-4514-b68e-68e0a531c947" />

Caso apareça erro ao processar a colicitação, significa que o back-end estava ligando. Aguarde um pouco para tentar entrar com a tela na página de login;

<img width="1841" height="1501" alt="image" src="https://github.com/user-attachments/assets/81c36291-5062-49f4-b2f5-7f010ea66e76" />

Este comportamento é esperado. Tente novamente clicando em entrar até conseguir cadastrar 

<img width="1841" height="1501" alt="image" src="https://github.com/user-attachments/assets/d81b3a15-0bdf-457a-a0d2-597513bd761d" />

Ao validar CPF positivamente, o usuário é redirecionado ao modal de preenchimento de nome para finalização de cadastro

<img width="1841" height="1501" alt="image" src="https://github.com/user-attachments/assets/48d03d5e-1f5e-4f61-9691-2a805a1bb775" />

Assim que logado no sistema, o usuário entra na tela de pautas onde é possível criar pauta e criar sessão;

A regra de negócio de pautas é: 
Uma pauta está aberta quando há empate de votação e não há sessões ocorrendo. 

Caso uma sessão termine em empate, a pauta pode ter novas sessões abertas.

Exclusão e edição de pautas não implementada para simular auditoria (soft delete) e para simplificação do projeto no caso da edição.

<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/0f7570a6-bb60-4cdc-8e99-accb267a5f7f" />

<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/69374eda-dcfa-4969-89bb-66cbc984d022" />

<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/94144c8e-6b1d-44d9-89f6-5b1e69dab768" />

<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/18b29cb4-327e-45f9-b9e4-b1903ca3d6ec" />

Ao clicar em Sessões de votação no header, é possível criar nova sessão com pautas com status em aberto, ver sessões criadas e caso haja sessões em votação, é possível votar nas sessões.

<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/c0b5accf-4dde-47ae-a941-bd9a22b30256" />

<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/71f9412d-62ab-40db-81de-7c9cfa972cb7" />

<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/0043d25d-f36c-49a8-84e2-9ecc87bd66f8" />



<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/07402e9a-e546-4c7c-8d7e-443480e1b86b" />


<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/042bf68a-77b5-4662-aa53-5e59159219c9" />

Foi implementada verificação do back até o front que impede o mesmo associado em votar duas vezes na sessão e verificação de término de sessão;

<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/c13edaf2-9749-492d-af6e-76d1484f274b" />

<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/6bfdc55f-c2d9-4ba5-9b47-0832b84e5957" />

<img width="1900" height="1801" alt="image" src="https://github.com/user-attachments/assets/f5169bf3-ea1e-4814-b4ef-65d682979b08" />

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

### Documentação da API (Swagger)


A aplicação possui documentação interativa da API via Swagger, permitindo explorar todos os endpoints, parâmetros e respostas diretamente pelo navegador.
<img width="4075" height="2147" alt="image" src="https://github.com/user-attachments/assets/570408a2-7cf0-4539-b605-68301963d31f" />

Caso não esteja conseguindo requisitar os endpoints, verifique no campo a seguir:
<img width="1968" height="163" alt="image" src="https://github.com/user-attachments/assets/e4616890-7923-4651-988d-4f9a917a0fc0" />


   

| Ambiente        | URL                                            |
| --------------- | ---------------------------------------------- |
| Local           | `http://localhost:8080/swagger-ui.html`        |
| Deploy (Render) | `https://desafio-votacao-fullstack-7qd4.onrender.com/swagger-ui/index.html` |

Também é possível acessar o JSON da API em /v3/api-docs.
Incluí o Swagger no deploy para que possam explorar e testar as APIs facilmente, sem precisar rodar nada localmente.


### Acesso
- Swagger: `http://localhost:8080/swagger-ui.html`
- Frontend: `http://localhost:5173`

---
## Decisões Técnicas Relevantes

- **DTOs + MapStruct**: Evitam exposição direta das entidades e simplificam serialização.
- **Redis**: Cache e performance para grandes volumes de votos.
- **React Query**: Gerenciamento eficiente de cache e requisições HTTP.
- **Soft Delete**: Preserva histórico de votações.
 - **Eventos e Scheduler**: Automatizam o encerramento das sessões de votação, garantindo que resultados sejam calculados no horário certo, mesmo que a aplicação reinicie ou haja sessões abertas esquecidas.
 Quando uma sessão é criada, ela é agendada para encerrar automaticamente no horário definido.

Se a aplicação reiniciar, todas as sessões abertas são reagendadas para não perder o controle do tempo.

Um monitoramento periódico verifica se há sessões que passaram do horário de encerramento e as finaliza.

Ao encerrar, o sistema calcula e armazena os resultados automaticamente.

---

## Testes & Performance

- **Integração**: Integração simples com redis(branch v0.8).
- **Performance**: Cenário de 100.000 votos processados em ~11s com Redis(branch v0.8).

---

## Limitações Conhecidas

- Servidores gratuitos podem entrar em idle, afetando integridade temporariamente.
- Segurança
- Para fins do desafio, todas as chamadas à API são consideradas autorizadas.
-  Não há autenticação ou controle de acesso implementados, já que a segurança pode ser abstraída conforme orientação do teste.
-  https://desafio-votacao-fullstack-front-v03.vercel.app/#{var}
-  #{var} Não sei se é possivel configurar pra conseguir entrar em qualquer url, mas atualmente, só é
-  possível navegar via fluxo comum, entrando via https://desafio-votacao-fullstack-front-v03.vercel.app e utilizando internamente



---

## Contribuição

1. Faça **fork** do repositório.
2. Implemente alterações.
3. Crie **Pull Request** para análise.
