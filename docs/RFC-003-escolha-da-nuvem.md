# RFC-003 — Escolha da nuvem

**Status:** Aceita

## Opções consideradas

| Critério | AWS | GCP | Azure |
|---|---|---|---|
| Exemplos citados no enunciado do desafio | API Gateway, Lambda, RDS | — | — |
| Free tier / maturidade para uso educacional | Amplo, bem documentado para estudantes | Bom, menos comum nos exemplos da FIAP | Bom, menos comum nos exemplos da FIAP |
| API Gateway + Serverless + K8s gerenciado no mesmo ecossistema | API Gateway (HTTP API) + Lambda + EKS | API Gateway + Cloud Functions + GKE | API Management + Functions + AKS |

## Decisão

**AWS**, com:
- **API Gateway (HTTP API)** roteando para a Lambda de autenticação.
- **AWS Lambda** (Python) para a Function Serverless de autenticação por CPF.
- **Amazon RDS PostgreSQL** como banco gerenciado.
- **Amazon EKS** como cluster Kubernetes.
- **AWS Load Balancer Controller** para expor a aplicação principal.

## Justificativa

1. O próprio enunciado do desafio já cita "AWS API Gateway" como exemplo — reduz ambiguidade na avaliação.
2. Todos os quatro repositórios pedidos (Lambda, infra K8s, infra banco, app) mapeiam 1:1 para serviços gerenciados da AWS (Lambda, EKS, RDS), sem gambiarra de tradução entre nuvens.
3. Documentação e tutoriais para uso educacional/free tier são mais abundantes para AWS no contexto do curso.

## Consequência prática desta fase

A conta usada é um **AWS Academy Learner Lab** (fornecida pela FIAP), não uma conta AWS "normal" — sua política de curso restringe permissões IAM e bloqueia por completo `rds:CreateDBInstance` (nem RDS clássico, nem Aurora). Duas adaptações resultaram disso, sem mudar a decisão de usar AWS: EKS/node group/ALB Controller usam a role `LabRole` pré-existente do lab em vez de roles IAM próprias (sem IRSA/OIDC provider — também bloqueados), e o Postgres roda como `StatefulSet` dentro do próprio EKS em vez de RDS (ver [RFC-001](./RFC-001-escolha-banco-de-dados.md)). O deploy real dos 4 repositórios foi feito com `terraform apply`/`kubectl` locais, usando as credenciais de sessão do Learner Lab — os pipelines de CI/CD continuam escritos e validados para uma conta AWS sem essa restrição (gate por variável `DEPLOY_TO_AWS`, ver README de cada repositório), que seria o caminho fora do contexto acadêmico deste desafio.
