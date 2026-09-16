# ADR-003 — API Gateway roteia só para a Lambda; app é exposta via ALB

**Status:** Aceita

## Contexto

O desafio pede "API Gateway para controle e roteamento" como peça obrigatória. Havia duas topologias possíveis: (a) todo o tráfego (auth + demais rotas da aplicação) passa pelo API Gateway, que faz proxy para o EKS via VPC Link; ou (b) o API Gateway cobre só o endpoint de autenticação, e a aplicação principal é exposta separadamente.

## Decisão

**Opção (b).** O API Gateway (HTTP API, no repositório `fiap-tc3-lambda-auth`) expõe exclusivamente `POST /auth/cpf`, integrado via `AWS_PROXY` com a Lambda. A aplicação principal é exposta por um `Service`/`Ingress` do Kubernetes, materializado como ALB pelo **AWS Load Balancer Controller** (Terraform em `fiap-tc3-infra-k8s`).

## Justificativa

1. **VPC Link tem custo e complexidade desproporcionais** para este escopo: exigiria um Network Load Balancer interno só para o API Gateway alcançar o EKS, além de manter o mapeamento de todas as rotas da aplicação (hoje ~30 endpoints) duplicado no Gateway.
2. O requisito textual do desafio ("Criar uma Function Serverless para... gerar e devolver um token JWT") liga o Gateway explicitamente ao fluxo de autenticação — cumprido plenamente pela topologia escolhida.
3. As "rotas sensíveis" continuam protegidas por JWT independente de qual componente de borda as expõe — o requisito de segurança é sobre autenticação/autorização na aplicação, não sobre qual produto de nuvem faz o roteamento.

## Consequência

Cliente final faz duas chamadas de rede distintas: uma ao endpoint do API Gateway para obter o token, outra ao endpoint do ALB para consumir a API — em vez de uma única base URL. Documentado no [diagrama de sequência](./diagrama-sequencia.md) e nos READMEs de `fiap-TC1-oficina` e `fiap-tc3-lambda-auth` para não gerar confusão na hora do vídeo de demonstração.
