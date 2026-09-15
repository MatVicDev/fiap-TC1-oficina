# Roteiro — Vídeo de demonstração (Tech Challenge Fase 3)

**Duração alvo: 13-14 min (limite: 15 min para o YouTube).** Tempos são aproximados — o importante é a ordem e não estourar o limite.

## Checklist antes de gravar

- [ ] Sessão do AWS Academy Learner Lab ativa (**Start Lab**, círculo verde) — confere tempo restante da sessão antes de começar a gravar.
- [ ] `kubectl get nodes` funcionando (kubeconfig atualizado: `aws eks update-kubeconfig --name fiap-tc3-oficina-homologacao --region us-east-1`).
- [ ] Abas do navegador já abertas: URL pública da app (`http://k8s-oficina-oficinaa-ee0871547f-1634370205.us-east-1.elb.amazonaws.com/v3/api-docs`), dashboard do Datadog, os 4 repositórios no GitHub.
- [ ] Postman com a collection `fiap-tc3-lambda-auth/docs/auth-cpf.postman_collection.json` importada, apontando para `https://1a58yfz4d3.execute-api.us-east-1.amazonaws.com/auth/cpf`.
- [ ] Um CPF já cadastrado no banco (via endpoint de clientes, autenticado como admin) para o teste da Lambda devolver 200 em vez de 404 — decida se quer mostrar o caminho feliz, o 404 ("cliente não cadastrado"), ou os dois.
- [ ] Terminal com fonte grande, `kubectl` e `terraform` no PATH.

---

## 1. Abertura (0:00–0:45)

- Nome, RM, Tech Challenge Fase 3 — SOAT/FIAP.
- Uma frase sobre o que a Fase 3 adiciona à Fase 2: nuvem real (AWS), autenticação serverless por CPF, banco gerenciado/observabilidade — Clean Architecture e Kubernetes já vinham da Fase 2.

## 2. Visão geral da arquitetura (0:45–3:00)

Mostrar `docs/diagrama-componentes.md` (renderizado no GitHub) enquanto narra:

- **4 repositórios**, um por responsabilidade do desafio:
  1. `fiap-tc3-lambda-auth` — Lambda Python + API Gateway (autenticação por CPF)
  2. `fiap-tc3-infra-k8s` — Terraform do EKS, VPC, ALB Controller, Datadog Agent
  3. `fiap-tc3-infra-db` — Terraform do banco
  4. `fiap-TC1-oficina` — aplicação Spring Boot + manifests Kubernetes
- Por que AWS: citado no próprio enunciado do desafio, e os 4 repositórios mapeiam 1:1 em serviços gerenciados (ver `RFC-003-escolha-da-nuvem.md`).
- Por que Lambda para o CPF: isola a única rota pública sem autenticação prévia, sem abrir o cluster inteiro (ver `RFC-002-estrategia-autenticacao-cpf.md` e `ADR-003-gateway-somente-para-lambda.md`).
- Datadog para observabilidade: métricas de cluster, logs estruturados em JSON, APM (traces do `dd-java-agent`).

## 3. O imprevisto: AWS Academy Learner Lab (3:00–4:30)

Esta seção é importante — mostra troubleshooting real, não só "seguiu o tutorial":

- A conta usada é um **AWS Academy Learner Lab** (fornecido pela FIAP), não uma conta AWS normal — ele restringe permissões IAM por política de curso.
- Duas descobertas que mudaram a implementação, confirmadas testando de verdade (não achismo):
  - **Não dá para criar roles/policies IAM próprias** — só existe a role pré-criada `LabRole`. Então EKS, node group e o AWS Load Balancer Controller usam `LabRole` direto, sem IRSA (que exigiria um OIDC provider, também bloqueado).
  - **RDS é bloqueado por completo** — nem instância clássica, nem a instância dentro de um cluster Aurora (é a mesma API `CreateDBInstance` por baixo dos panos). Solução: **Postgres roda como StatefulSet dentro do próprio EKS**, com volume EBS via CSI driver, banco idêntico (mesma engine, mesmas queries), só a forma de provisionar mudou.
- Mensagem central: a arquitetura documentada nos RFCs/ADRs é a arquitetura "ideal"; o Learner Lab forçou 2 adaptações pontuais, documentadas no próprio Terraform (comentários) e no histórico de commits.

## 4. Tour rápido pelo código (4:30–6:00)

- Abrir os 4 repositórios no GitHub, mostrar rapidamente:
  - Estrutura de pastas do `fiap-TC1-oficina` (Clean Architecture: domain/application/infra).
  - `fiap-tc3-infra-k8s/terraform/eks.tf` — cluster + node group usando `LabRole`.
  - `fiap-tc3-infra-db/terraform/main.tf` — StatefulSet do Postgres.
  - `fiap-tc3-lambda-auth/src/lambda_function.py` — validação de CPF + emissão de JWT com o mesmo formato de claims do monólito.
- Não precisa ler linha por linha — o objetivo é provar que o código existe e bate com o que foi decidido nos RFCs/ADRs.

## 5. Demonstração ao vivo (6:00–11:00) — a parte mais importante

1. **Cluster no ar** (terminal):
   ```
   kubectl get nodes
   kubectl get pods -n oficina
   kubectl get pods -n database
   ```
2. **App pública via ALB** (navegador): abrir a URL do Swagger (`/v3/api-docs` ou `/swagger-ui.html` se existir) — mostra que não é `localhost`, é um ALB real da AWS.
3. **Login como admin** (Postman ou `curl`):
   ```
   POST /auth/login  {"usuario":"admin","senha":"..."}
   ```
   → mostrar o JWT retornado.
4. **Autenticação por CPF via Lambda** (Postman, na collection já importada):
   - Chamar o endpoint do API Gateway com um CPF válido.
   - Mostrar o JWT retornado (mesmo formato de claims — reforçar que o `JWTFilter` da aplicação aceita os dois sem diferenciar a origem).
   - Opcional: mostrar também o CPF **não cadastrado** devolvendo 404, prova de que a Lambda está de fato consultando o Postgres em tempo real (não é mock).
5. **Usar o JWT** para acessar um endpoint protegido (ex.: criar ou listar uma Ordem de Serviço) — fecha o ciclo: autenticação → autorização → operação de negócio.
6. **Autoscaling** (opcional, se der tempo): `kubectl get hpa -n oficina` — mostrar que o HPA está configurado (não precisa gerar carga ao vivo, só mostrar a config).

## 6. Observabilidade (11:00–12:30)

- Abrir o dashboard do Datadog:
  - Infraestrutura: os 2 nós do EKS, uso de CPU/memória.
  - Logs: filtrar por `service:oficina-app`, mostrar os logs estruturados em JSON.
  - APM: um trace de uma das chamadas feitas na demo (login ou criação da OS), mostrando a integração do `dd-java-agent`.

## 7. Encerramento (12:30–13:30)

- Recapitular em uma frase: 4 repositórios, EKS + Postgres no cluster + Lambda serverless + Datadog, tudo rodando de verdade na AWS.
- Mencionar rapidamente o principal trade-off assumido por escopo do desafio (ex.: master user compartilhado entre app e Lambda — `ADR-004`) e o que seria diferente em produção real.
- Agradecimento e fechamento.

---

## Notas para depois da gravação

- ~~`README.md` e `RFC-003`/`ADR-004` desatualizados~~ — já corrigido: todos os READMEs, RFCs, ADRs e diagramas dos 4 repositórios refletem a arquitetura real (Postgres no EKS, `LabRole`, Learner Lab). Nada pendente aqui antes da entrega.
