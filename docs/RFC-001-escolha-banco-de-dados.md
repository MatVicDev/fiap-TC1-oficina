# RFC-001 — Escolha do banco de dados e ajustes no modelo relacional

**Status:** Aceita
**Autor:** Matheus Victor Moreira Mendes
**Contexto:** Tech Challenge Fase 3 (SOAT/FIAP)

## Problema

A Fase 2 já usava PostgreSQL rodando como Deployment dentro do próprio cluster Kubernetes. A Fase 3 exige um **banco de dados gerenciado** e pede justificativa formal da escolha, além de ajustes no modelo relacional com diagrama ER.

## Opções consideradas

| Opção | Prós | Contras |
|---|---|---|
| **PostgreSQL (RDS)** | Já era o banco da Fase 2 (zero migração de schema/JPA), suporte nativo a `UUID`, `ENUM` via `@Enumerated(STRING)`, JSON, forte consistência transacional, RDS com backup/patch/Multi-AZ gerenciados | Nenhum bloqueio identificado para o domínio atual |
| MySQL (RDS) | Também maduro e gerenciado | Suporte a UUID/ENUM menos natural; migraria schema sem ganho real |
| DynamoDB (NoSQL) | Escala horizontal simples, serverless | Domínio tem relacionamentos fortes (Cliente → Veículo → OrdemServico → ItemOS) e múltiplas consultas por atributos diferentes — modelar isso em NoSQL exigiria desnormalização/índices secundários sem necessidade real de escala desse porte |

## Decisão

Manter **PostgreSQL**, migrando de "Deployment dentro do cluster" para **Amazon RDS PostgreSQL 16 gerenciado** (repositório [`fiap-tc3-infra-db`](https://github.com/MatVicDev/fiap-tc3-infra-db)).

Justificativa:
1. O domínio tem relacionamentos bem definidos e cardinalidade conhecida (ver diagrama ER abaixo) — casa naturalmente com um modelo relacional.
2. Zero mudança de schema/ORM: a aplicação já usa JPA/Hibernate sobre Postgres desde a Fase 1.
3. RDS resolve exatamente a lacuna da Fase 2 (backup automático, patching, Multi-AZ, credenciais rotacionadas via Secrets Manager) sem trocar de motor.
4. Volume e padrão de acesso (CRUD transacional, poucas leituras analíticas pesadas) não justificam NoSQL.

## Ajuste no modelo relacional

Adicionado o campo `status` (`ATIVO`/`INATIVO`) na tabela `clientes`, necessário para a Function Serverless de autenticação por CPF conseguir recusar login de clientes inativos sem precisar de uma tabela/flag adicional. Ver [`ER-diagrama.md`](./ER-diagrama.md) para o modelo completo e explicação dos relacionamentos.
