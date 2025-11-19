Desafio Votação - Sistema de Gerenciamento de Assembleias
Descrição do Projeto

Este projeto implementa um sistema de votação online para cooperativas, permitindo que associados cadastrem pautas, participem de sessões de votação e visualizem os resultados de forma segura e persistente. A solução é construída com Java Spring Boot no backend e React no frontend, podendo ser executada na nuvem.

Funcionalidades implementadas:

Cadastro de novas pautas.

Abertura de sessões de votação (tempo configurável, default 1 minuto).

Recebimento de votos Sim/Não por associado (cada associado só pode votar uma vez por pauta).

Contabilização automática dos votos e exibição do resultado da pauta.

Persistência dos dados (pautas e votos) mesmo após reinicialização da aplicação.

Testes de performance para cenários com grande volume de votos.

Funcionalidades pendentes ou parciais:

Documentação da API via Swagger (planejada).

Algumas funcionalidades do frontend ainda em implementação.

Escalar votação sem schedule
--
Tecnologias Utilizadas

Backend: 

Frontend:

Testes:

Outros:

Funcionalidades Bônus Implementadas:

Integração com sistemas externos:

Fake client para validação de CPF.

Retorna status aleatório ABLE_TO_VOTE ou UNABLE_TO_VOTE.

CPF inválido retorna 404.

Performance:

Testes de performance implementados para simular centenas de milhares de votos com redis.

Versionamento da API:

Estratégia de versionamento via URL (/api/v1/...)

Estrutura do Projeto
backend/
 ├─ src/main/java/...          # Código fonte do backend
 ├─ src/test/java/...          # Testes unitários
 └─ resources/
frontend/
 ├─ src/                      # Código fonte do frontend
 └─ public/
docker/
 ├─ docker-compose.yml        # Configuração para  app

Pré-requisitos

Java 17

Node.js >= 18

PostgreSQL >= 15


Como Executar
Backend

API disponível em http://localhost:8080/api/v1

Frontend

Instale dependências:

cd frontend
npm install


Execute o frontend:



Testes de Performance


Isso iniciará o banco PostgreSQL e o backend já configurado para uso local.

Endpoints Principais (REST API)

POST /api/v1/pautas – Criar nova pauta

POST /api/v1/pautas/{id}/sessao – Abrir sessão de votação

POST /api/v1/votos – Registrar voto do associado



(A documentação Swagger ainda será adicionada em breve.)

Considerações de Arquitetura(não necessariamente todas implementadas):

Uso de DTOs para comunicação entre frontend e backend.

Persistência com JPA/Hibernate.

Mensageria e threads para controle de sessões e contagem de votos.

Versionamento da API via URL para facilitar evolução futura sem quebrar clientes.

Testes unitários e de integração garantindo robustez do sistema.

Logs e Monitoramento

Logs configurados via Spring Boot (logback-spring.xml).

Informações de sessão, votos recebidos e erros críticos registrados.

Observações

Todas as chamadas à API são consideradas autorizadas para fins de teste.

A solução é projetada para ser escalável e suportar grande volume de votos.

A documentação Swagger será adicionada futuramente para facilitar testes de integração.
