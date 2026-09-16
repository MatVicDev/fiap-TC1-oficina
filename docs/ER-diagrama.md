# Diagrama ER — Modelo Relacional

Ver [`ADR-005`](./ADR-005-fk-logica-entre-agregados.md) para a justificativa de por que a maioria das referências entre tabelas é uma **FK lógica** (coluna UUID simples, sem `FOREIGN KEY` no schema) em vez de uma FK física — e por que isso não significa abrir mão de performance: todas essas colunas agora têm índice explícito (ver Fase 3, seção "Ajustes desta fase" abaixo).

```mermaid
erDiagram
    CLIENTES ||--o{ VEICULOS : "possui (cpf_proprietario)"
    CLIENTES ||--o{ ORDENS_SERVICOS : "solicita (cliente_id)"
    VEICULOS ||--o{ ORDENS_SERVICOS : "é atendido em (veiculo_id)"
    ORDENS_SERVICOS ||--o{ ITENS_ORDEM_SERVICO : "contém"
    ORDENS_SERVICOS ||--o| ORCAMENTOS : "gera"
    INSUMOS ||--o{ ITENS_ORDEM_SERVICO : "consumido em (insumo_id)"
    INSUMOS ||--|| ESTOQUES : "tem (insumo_id)"

    CLIENTES {
        uuid id PK
        string nome
        string cpf UK
        string telefone
        string email
        string status "ATIVO | INATIVO (Fase 3)"
        timestamp data_cadastro
    }

    VEICULOS {
        uuid id PK
        string placa UK
        string marca
        string modelo
        int ano
        string cor
        string cpf_proprietario FK_logica "idx (Fase 3)"
    }

    ORDENS_SERVICOS {
        uuid id PK
        uuid cliente_id FK_logica "idx (Fase 3)"
        uuid veiculo_id FK_logica "idx (Fase 3)"
        string status "idx (Fase 3)"
        string sintoma_relatado
        uuid orcamento_id FK
        decimal valor_total
        timestamp data_inicio
        timestamp data_entrega
    }

    ITENS_ORDEM_SERVICO {
        uuid id PK
        uuid ordem_servico_id FK "idx (Fase 3)"
        uuid insumo_id FK_logica "idx (Fase 3)"
        string descricao
        decimal preco_unitario
        int quantidade
    }

    ORCAMENTOS {
        uuid id PK
        decimal valor_total
        string status
        timestamp data_geracao
    }

    INSUMOS {
        uuid id PK
        string nome
        string descricao
        decimal preco_base
        string tipo
    }

    ESTOQUES {
        uuid id PK
        uuid insumo_id FK_logica "UNIQUE idx (Fase 3)"
        int quantidade
    }

    SERVICOS {
        uuid id PK
        string nome
        string descricao
        decimal preco_base
        int tempo_previsto
    }
```

> `SERVICOS` é um catálogo hoje desacoplado de `ITENS_ORDEM_SERVICO` (só insumos compõem os itens de uma OS na implementação atual) — mantido no diagrama por fazer parte do domínio e ser exposto via API, mas sem relacionamento de FK a documentar.

## Explicação dos relacionamentos

- **Cliente → Veículo (1:N)**: um cliente pode ter vários veículos; a referência é o CPF do proprietário gravado no veículo, não um UUID de cliente — reflexo de como o cadastro de veículo foi modelado desde a Fase 1.
- **Cliente → Ordem de Serviço (1:N)** e **Veículo → Ordem de Serviço (1:N)**: cada OS pertence a exatamente um cliente e um veículo daquele cliente.
- **Ordem de Serviço → Item OS (1:N)**: única relação com FK física de fato (`@ManyToOne` + `@JoinColumn(ordem_servico_id)`), porque item de OS não faz sentido fora do agregado `OrdemServico`.
- **Ordem de Serviço → Orçamento (1:1)**: o orçamento é gerado a partir dos itens da OS no momento em que o diagnóstico é concluído.
- **Insumo → Item OS (1:N)** e **Insumo → Estoque (1:1)**: um insumo tem exatamente um registro de estoque (índice único adicionado na Fase 3), e pode aparecer em vários itens de diferentes OS.

## Ajustes desta fase (Fase 3)

1. **Campo `status` em `clientes`** (`ATIVO`/`INATIVO`) — necessário para a Function Serverless de autenticação por CPF recusar login de cliente inativo (ver [RFC-002](./RFC-002-estrategia-autenticacao-cpf.md)).
2. **Índices explícitos** nas colunas usadas como referência cruzada entre agregados, que antes não tinham índice: `ordens_servicos.cliente_id`, `ordens_servicos.veiculo_id`, `ordens_servicos.status`, `itens_ordem_servico.ordem_servico_id`, `itens_ordem_servico.insumo_id`, `veiculos.cpf_proprietario`, e um índice **único** em `estoques.insumo_id` (formaliza no schema a invariante de "um estoque por insumo" que já era garantida pela aplicação). Sem índice, toda consulta por essas colunas (ex.: listar OS de um cliente, achar estoque de um insumo) forçava `seq scan` — problema real de performance à medida que as tabelas crescem, que é exatamente o que a Fase 3 pede para resolver.
