# RFC-001 — Escolha do banco de dados e ajustes no modelo relacional

**Status:** Aceita
**Autor:** Matheus Victor Moreira Mendes
**Contexto:** Tech Challenge Fase 3 (SOAT/FIAP)

## Problema

A Fase 2 já usava PostgreSQL rodando como Deployment dentro do próprio cluster Kubernetes. A Fase 3 exige um **banco de dados gerenciado** e pede justificativa formal da escolha, além de ajustes no modelo relacional com diagrama ER.

## Opções consideradas

| Opção | Prós | Contras |
|---|---|---|
| **PostgreSQL (gerenciado)** | Já era o banco da Fase 2 (zero migração de schema/JPA), suporte nativo a `UUID`, `ENUM` via `@Enumerated(STRING)`, JSON, forte consistência transacional, backup/patch/Multi-AZ gerenciados | Nenhum bloqueio identificado para o domínio atual |
| MySQL (gerenciado) | Também maduro e gerenciado | Suporte a UUID/ENUM menos natural; migraria schema sem ganho real |
| DynamoDB (NoSQL) | Escala horizontal simples, serverless | Domínio tem relacionamentos fortes (Cliente → Veículo → OrdemServico → ItemOS) e múltiplas consultas por atributos diferentes — modelar isso em NoSQL exigiria desnormalização/índices secundários sem necessidade real de escala desse porte |

## Decisão

Manter **PostgreSQL**, migrando de "Deployment dentro do cluster gerenciado à mão" para **Postgres provisionado como código** pelo repositório [`fiap-tc3-infra-db`](https://github.com/MatVicDev/fiap-tc3-infra-db).

Justificativa:
1. O domínio tem relacionamentos bem definidos e cardinalidade conhecida (ver diagrama ER abaixo) — casa naturalmente com um modelo relacional.
2. Zero mudança de schema/ORM: a aplicação já usa JPA/Hibernate sobre Postgres desde a Fase 1.
3. Um banco gerenciado (backup automático, patching, Multi-AZ, credenciais rotacionadas via Secrets Manager) resolve exatamente a lacuna da Fase 2, sem trocar de motor.
4. Volume e padrão de acesso (CRUD transacional, poucas leituras analíticas pesadas) não justificam NoSQL.

### Nota de implementação (Fase 3, execução real)

A decisão acima previa **Amazon RDS PostgreSQL** como o "banco gerenciado". Na conta usada de fato (AWS Academy Learner Lab, fornecida pela FIAP), `rds:CreateDBInstance` é bloqueado por política do curso — sem exceção, nem para uma instância clássica nem para a instância dentro de um cluster Aurora Serverless (é a mesma API por baixo dos panos; confirmado testando o `terraform apply` real, não só lendo a política). Diante disso, o Postgres passou a rodar como `StatefulSet` **dentro do próprio cluster EKS** (Terraform de `fiap-tc3-infra-db`, usando os providers `kubernetes`), com volume persistente via EBS CSI driver. A engine, o schema e todo o código da aplicação continuam exatamente os mesmos — só a forma de provisionar o Postgres mudou, por uma restrição do ambiente de laboratório e não por uma reavaliação da escolha de banco em si.

## Ajuste no modelo relacional

Adicionado o campo `status` (`ATIVO`/`INATIVO`) na tabela `clientes`, necessário para a Function Serverless de autenticação por CPF conseguir recusar login de clientes inativos sem precisar de uma tabela/flag adicional. Ver [`ER-diagrama.md`](./ER-diagrama.md) para o modelo completo e explicação dos relacionamentos.
