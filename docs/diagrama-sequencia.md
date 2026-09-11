# Diagrama de Sequência — Autenticação por CPF + Abertura de Ordem de Serviço

```mermaid
sequenceDiagram
    actor C as Cliente final
    participant GW as API Gateway
    participant L as Lambda auth-cpf
    participant SM as Secrets Manager
    participant DB as RDS PostgreSQL
    participant ALB as ALB
    participant App as App (EKS)

    C->>GW: POST /auth/cpf { cpf }
    GW->>L: invoke (AWS_PROXY)
    L->>L: valida dígitos verificadores do CPF
    alt CPF inválido
        L-->>C: 400 CPF inválido
    else CPF válido
        L->>DB: SELECT status FROM clientes WHERE cpf = ?
        alt cliente não encontrado
            L-->>C: 404 Cliente não cadastrado
        else cliente INATIVO
            L-->>C: 403 Cliente inativo
        else cliente ATIVO
            L->>SM: GetSecretValue (JWT secret)
            L->>L: gera JWT { sub: cpf, role: CLIENTE, exp }
            L-->>C: 200 { token }
        end
    end

    Note over C,App: Cliente já autenticado — abertura de Ordem de Serviço

    C->>ALB: POST /ordens-servico\nAuthorization: Bearer <jwt role=CLIENTE>
    ALB->>App: encaminha requisição
    App->>App: JWTFilter valida assinatura + expiração
    App->>App: role=CLIENTE → autentica direto das claims (sem UserDetailsService)
    App->>App: CriarOrdemServicoUseCase
    App->>DB: INSERT ordens_servicos (status=RECEBIDA)
    App-->>C: 201 Created { ordemServicoId, status: RECEBIDA }
```

## Pontos de atenção documentados

- A autenticação (via API Gateway) e a operação de negócio (via ALB) são **dois endpoints/domínios distintos** — ver [ADR-003](./ADR-003-gateway-somente-para-lambda.md). O cliente troca de host entre a etapa de login e as etapas seguintes.
- O `JWTFilter` da aplicação não distingue "de onde" veio a chamada, só a assinatura e o claim `role` do token — por isso o mesmo filtro aceita tokens do login admin (`role=ADMIN`, verificados contra o `UserDetailsService`) e da Lambda (`role=CLIENTE`, autenticado direto das claims). Detalhes em [RFC-002](./RFC-002-estrategia-autenticacao-cpf.md).
- Todo log de ambas as etapas carrega um `correlationId` (gerado pela Lambda ou pelo `CorrelationIdFilter` da aplicação, propagado via header `X-Correlation-Id`), permitindo juntar num único dashboard do Datadog os logs de uma mesma sessão do cliente mesmo atravessando dois serviços diferentes.
