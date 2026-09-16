# RFC-002 — Estratégia de autenticação por CPF

**Status:** Aceita
**Contexto:** Tech Challenge Fase 3 (SOAT/FIAP)

## Problema

O desafio exige uma Function Serverless que valide o CPF do cliente, consulte sua existência/status no banco e emita um JWT válido para consumo das APIs protegidas. Duas decisões de design precisavam ser tomadas: (1) como a Lambda consulta o cliente, e (2) como o token que ela emite é aceito pela aplicação principal, que já tem seu próprio mecanismo JWT (login usuário/senha para admin).

## Opção 1 — Lambda consulta a aplicação principal via API interna

A Lambda chamaria um endpoint interno (`GET /internal/clientes/cpf/{cpf}`) exposto pelo monólito.

**Rejeitada porque:**
- Criaria um endpoint sem autenticação (a própria Lambda ainda não tem token nesse ponto do fluxo) ou exigiria uma segunda credencial só para esse acesso interno — complexidade sem benefício real.
- Acopla a disponibilidade da autenticação à disponibilidade do monólito: se a aplicação principal cair, ninguém consegue mais logar, mesmo que o banco esteja saudável.

## Opção 2 (escolhida) — Lambda consulta o RDS diretamente

A Lambda se conecta diretamente ao PostgreSQL gerenciado (mesma rede privada, via VPC), executa `SELECT status FROM clientes WHERE cpf = ?` e decide a resposta.

**Motivos:**
- Desacopla a autenticação da aplicação principal — alinhado com o espírito "serverless" do requisito (função independente, single-purpose).
- É a mesma fonte de dados (uma tabela, sem lógica de negócio complexa por trás da consulta), então não há necessidade de passar pela camada de aplicação do monólito.
- Credenciais do RDS e a chave JWT ficam no Secrets Manager, nunca hardcoded (ver Terraform de [`fiap-tc3-lambda-auth`](https://github.com/MatVicDev/fiap-tc3-lambda-auth)).

**Trade-off aceito:** a Lambda precisa conhecer o schema da tabela `clientes` (acoplamento a nível de dado, não de API). Aceitável porque schema de autenticação muda raramente e o time é o mesmo em ambos os repositórios.

## Como o token é aceito pela aplicação principal

A aplicação já emitia JWT HS256 para o login admin (usuário/senha). Em vez de dois mecanismos de verificação paralelos, a Lambda **assina com o mesmo segredo** (compartilhado via Secrets Manager) e usa os mesmos claims (`sub`, `iat`, `exp`), adicionando um claim `role`:

- `role: "ADMIN"` → emitido pelo `/auth/login` do monólito (login usuário/senha), `sub` = username.
- `role: "CLIENTE"` → emitido pela Lambda (login por CPF), `sub` = CPF.

O `JWTFilter` da aplicação (`infrastructure/security/JWTFilter.java`) lê o claim `role`: para `ADMIN`, continua resolvendo via `UserDetailsService` (comportamento inalterado); para `CLIENTE`, monta a autenticação direto das claims do token (não existe — nem deveria existir — um `UserDetails` para cada cliente final). Ver [ADR-003](./ADR-003-gateway-somente-para-lambda.md) para a decisão de topologia de rede desse fluxo.

## Escopo não coberto nesta fase

RBAC fino por rota (ex.: cliente só vê a própria OS) não foi implementado — qualquer token válido (ADMIN ou CLIENTE) continua liberando as rotas hoje marcadas como autenticadas, igual à Fase 2. Ficou registrado como melhoria futura porque a Fase 3 pede "proteger rotas sensíveis com autenticação via CPF" (satisfeito: CPF passou a ser um caminho válido para obter token), não uma reformulação completa do modelo de autorização.
