# Diagrama de Componentes — Visão Fase 3

Cobre nuvem, APIs, banco e monitoramento, com a fronteira de cada um dos 4 repositórios marcada.

```mermaid
flowchart TB
    subgraph Cliente["Clientes"]
        Admin["Admin (usuário/senha)"]
        ClienteFinal["Cliente final (CPF)"]
    end

    subgraph Repo1["fiap-tc3-lambda-auth"]
        APIGW["API Gateway (HTTP API)\nPOST /auth/cpf"]
        Lambda["Lambda: auth-cpf\n(Python)"]
        APIGW --> Lambda
    end

    subgraph AWS["AWS"]
        subgraph Repo2["fiap-tc3-infra-k8s"]
            ALB["ALB\n(AWS Load Balancer Controller)"]
            subgraph EKS["EKS — namespace oficina"]
                App["fiap-TC1-oficina\nDeployment (2-5 réplicas via HPA)"]
                DDAgent["Datadog Agent\n(DaemonSet)"]
            end
            ALB --> App
        end

        subgraph Repo3["fiap-tc3-infra-db"]
            RDS[("RDS PostgreSQL 16")]
        end

        SM["Secrets Manager\n(JWT secret, credenciais RDS)"]
        SSM["SSM Parameter Store\n(outputs cruzados entre repos)"]
    end

    subgraph Observabilidade["Observabilidade"]
        Datadog[("Datadog SaaS\nAPM + Logs + Dashboards")]
    end

    subgraph CICD["CI/CD — GitHub Actions (um pipeline por repositório)"]
        GHCR["GHCR\n(imagem da aplicação)"]
    end

    Admin -->|"POST /auth/login"| App
    ClienteFinal -->|"POST /auth/cpf"| APIGW
    Lambda -->|consulta cliente| RDS
    Lambda -->|lê segredos| SM
    Lambda -.publica/lê.-> SSM
    Repo3 -.publica/lê.-> SSM
    Repo2 -.publica/lê.-> SSM
    Lambda -->|JWT role=CLIENTE| ClienteFinal
    ClienteFinal -->|"Authorization: Bearer <jwt>"| ALB
    App --> RDS
    App -->|traces/logs| DDAgent --> Datadog
    GHCR --> App

    style Repo1 stroke-dasharray: 4 4
    style Repo2 stroke-dasharray: 4 4
    style Repo3 stroke-dasharray: 4 4
```

## Os 4 repositórios e o que cada um provisiona/executa

| # | Repositório | Conteúdo |
|---|---|---|
| 1 | [`fiap-tc3-lambda-auth`](https://github.com/MatVicDev/fiap-tc3-lambda-auth) | Function Serverless de autenticação por CPF + API Gateway (Terraform) |
| 2 | [`fiap-tc3-infra-k8s`](https://github.com/MatVicDev/fiap-tc3-infra-k8s) | VPC, EKS, node group com autoscaling, AWS Load Balancer Controller, Datadog Agent (Terraform) |
| 3 | [`fiap-tc3-infra-db`](https://github.com/MatVicDev/fiap-tc3-infra-db) | RDS PostgreSQL gerenciado (Terraform) |
| 4 | [`fiap-TC1-oficina`](https://github.com/MatVicDev/fiap-TC1-oficina) | Aplicação principal (Spring Boot) + manifests Kubernetes + Dockerfile |

Ligação entre repositórios independentes: SSM Parameter Store, não Terraform remote state (ver seção "Rede" do README de `fiap-tc3-infra-db` e `fiap-tc3-lambda-auth`) — cada repositório tem pipeline e ciclo de vida próprios.
