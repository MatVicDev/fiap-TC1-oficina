# ADR-004 — Master user compartilhado entre app e Lambda (trade-off aceito)

**Status:** Aceita, com melhoria futura registrada

## Contexto

Tanto a aplicação principal (via JPA/Hibernate) quanto a Lambda de autenticação por CPF precisam de credenciais para o Postgres. O ideal em produção é um usuário de aplicação com permissões mínimas (`SELECT/INSERT/UPDATE/DELETE` só nas tabelas necessárias), separado do master user administrativo do banco.

## Decisão

Para o escopo deste desafio, **app e Lambda usam o mesmo master user** do banco, gerado pelo Terraform de `fiap-tc3-infra-db` (`random_password`) e publicado no Secrets Manager no mesmo formato JSON (`username`/`password`) que um RDS com `manage_master_user_password = true` geraria — a Lambda e o job de deploy da aplicação leem esse secret sem saber (nem precisar saber) se por trás existe um RDS ou um Postgres rodando dentro do EKS.

> Nota de implementação: a intenção original era usar exatamente `manage_master_user_password` de um RDS de verdade. A conta usada neste desafio (AWS Academy Learner Lab) bloqueia `rds:CreateDBInstance`, então o Postgres roda como `StatefulSet` no próprio EKS (ver [RFC-001](./RFC-001-escolha-banco-de-dados.md)) — a credencial é gerada e guardada da mesma forma, só a origem do Postgres mudou. O trade-off de master user compartilhado discutido aqui é o mesmo independentemente de onde o Postgres roda.

## Justificativa do trade-off

- Criar um usuário de aplicação exigiria um passo adicional de bootstrap (rodar SQL de `CREATE ROLE`/`GRANT` logo após o `terraform apply`/subida do StatefulSet) — complexidade extra sem alterar o resultado observável no vídeo de demonstração ou nos requisitos avaliados.
- A senha já vem gerada aleatoriamente e versionada só no Secrets Manager, o que resolve a parte de "credencial não hardcoded" — o ponto de segurança mais visado pelo desafio.

## Consequência e melhoria futura

Tanto a aplicação (via Hibernate `ddl-auto=update`) quanto a Lambda (via `SELECT`) têm, na prática, permissão de superusuário sobre o schema. Caso o projeto continue além do desafio, a melhoria natural é: criar um usuário `oficina_app` com permissões restritas via script de bootstrap pós-deploy, e trocar as referências de `db_secret_arn` desse usuário em vez do master user — sem mudança de código na aplicação ou na Lambda, só no Terraform de `fiap-tc3-infra-db`.
