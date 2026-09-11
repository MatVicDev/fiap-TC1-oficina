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

No momento da implementação, a conta AWS do desafio ainda não foi liberada pela FIAP (créditos pendentes). Por isso: todo o Terraform, código da Lambda e pipelines de CI/CD foram escritos e têm sua sintaxe validada (`terraform validate`, testes unitários), mas o `terraform apply` real e o deploy em produção ficam para quando a conta existir — os pipelines já estão preparados para isso (gate por variável `DEPLOY_TO_AWS`, ver README de cada repositório).
