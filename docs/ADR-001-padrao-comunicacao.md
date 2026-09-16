# ADR-001 — Padrão de comunicação: REST síncrono + webhook assíncrono

**Status:** Aceita (herdada da Fase 1/2, reafirmada na Fase 3)

## Contexto

O sistema tem dois tipos de interação bem distintos: (1) operações CRUD e de transição de estado da OS, feitas por um agente que espera resposta imediata; (2) a decisão de orçamento, que vem de um "sistema externo" e pode demorar (aprovação humana em outro sistema).

## Decisão

- **REST síncrono (HTTP/JSON)** para todas as operações que o cliente/admin inicia e espera confirmação na hora: CRUD de clientes/veículos/insumos/serviços, transições de status da OS, login (admin e, na Fase 3, CPF via Lambda).
- **Webhook assíncrono** (`POST /webhooks/ordens-servico/{id}/orcamento`) para a decisão de orçamento vinda de sistema externo — desacopla o tempo de resposta desse sistema do fluxo síncrono da API.

## Por que não filas (SQS/SNS) para tudo

Considerado para o fluxo de orçamento, mas descartado: o "sistema externo de orçamento" já é modelado como caller HTTP (ele decide quando chamar o webhook), não como algo que a oficina precisa notificar de forma assíncrona e resiliente com reentrega garantida. Fila adicionaria infraestrutura sem um problema real de confiabilidade a resolver no escopo atual — fica como candidato natural caso o volume ou a necessidade de reentrega apareçam.

## Consequência para a Fase 3

A Function Serverless de autenticação por CPF segue o mesmo padrão síncrono: o cliente chama, espera o JWT na resposta. Não há fila entre API Gateway e Lambda.
