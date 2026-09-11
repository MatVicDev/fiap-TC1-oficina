# ADR-004 — RDS: master user compartilhado entre app e Lambda (trade-off aceito)

**Status:** Aceita, com melhoria futura registrada

## Contexto

Tanto a aplicação principal (via JPA/Hibernate) quanto a Lambda de autenticação por CPF precisam de credenciais para o RDS PostgreSQL. O ideal em produção é um usuário de aplicação com permissões mínimas (`SELECT/INSERT/UPDATE/DELETE` só nas tabelas necessárias), separado do master user administrativo do banco.

## Decisão

Para o escopo deste desafio, **app e Lambda usam o mesmo master user** do RDS, criado e gerenciado automaticamente pela AWS (`manage_master_user_password = true` no Terraform de `fiap-tc3-infra-db`), com a senha rotacionada no Secrets Manager.

## Justificativa do trade-off

- Criar um usuário de aplicação exigiria um passo adicional de bootstrap (rodar SQL de `CREATE ROLE`/`GRANT` contra o RDS logo após o `terraform apply`, via `null_resource` + `psql` ou uma Lambda de migração) — complexidade extra sem alterar o resultado observável no vídeo de demonstração ou nos requisitos avaliados.
- O master user já vem com rotação de senha gerenciada pela AWS, o que resolve a parte de "credencial não hardcoded" — o ponto de segurança mais visado pelo desafio.

## Consequência e melhoria futura

Tanto a aplicação (via Hibernate `ddl-auto=update`) quanto a Lambda (via `SELECT`) têm, na prática, permissão de superusuário sobre o schema. Caso o projeto continue além do desafio, a melhoria natural é: criar um usuário `oficina_app` com permissões restritas via script de bootstrap pós-`terraform apply`, e trocar as referências de `db_secret_arn` desse usuário em vez do master user — sem mudança de código na aplicação ou na Lambda, só no Terraform de `fiap-tc3-infra-db`.
