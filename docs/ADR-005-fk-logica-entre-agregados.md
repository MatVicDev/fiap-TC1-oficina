# ADR-005 — Manter FK lógica (não física) entre agregados, com índices explícitos

**Status:** Aceita

## Contexto

A maioria das referências cruzadas do modelo (`OrdemServico.clienteId`, `OrdemServico.veiculoId`, `ItemOS.insumoId`, `Veiculo.cpfProprietario`) são colunas UUID/String simples, sem `FOREIGN KEY` no schema do banco — diferente de `ItemOS → OrdemServico`, que é uma FK física real (`@ManyToOne` + `@JoinColumn`). A Fase 3 pede para "melhorar a modelagem do banco garantindo consistência e performance", o que levanta a pergunta: essas referências deveriam virar FK física?

## Decisão

**Não.** Mantemos FK lógica entre agregados de bounded contexts diferentes (Cliente, Veículo, Insumo são agregados próprios; OrdemServico é outro agregado que só referencia os outros por ID) e adicionamos **índices explícitos** nessas colunas para resolver a parte de performance sem violar a independência dos agregados.

## Justificativa

1. **DDD/Clean Architecture**: o projeto já separa `domain` por contexto (`cliente`, `veiculo`, `ordemServico`, `insumo`, `estoque`, `servico`) justamente para que cada agregado possa evoluir/persistir de forma independente. Uma FK física do banco acopla o schema de `ordens_servicos` ao de `clientes` e `veiculos` no nível de infraestrutura, contradizendo esse desenho.
2. **Item OS é exceção correta**: `ItemOS` não existe fora do agregado `OrdemServico` (não tem repositório próprio, não é acessado isoladamente) — por isso, ali sim, FK física com `CascadeType.ALL` faz sentido e já era assim desde a Fase 1.
3. **O gap real era performance, não consistência do dado em si**: nenhum bug de integridade referencial foi identificado (a aplicação sempre gera os UUIDs antes de referenciá-los); o problema é que consultas por essas colunas rodavam sem índice. Resolvido em todas elas (ver [ER-diagrama.md](./ER-diagrama.md)).

## Consequências

- Não há garantia do banco contra um `clienteId` "órfão" em `OrdemServico` — a integridade continua sendo responsabilidade da camada de aplicação (como já era). Aceito porque nenhum fluxo hoje permite excluir um Cliente/Veículo/Insumo referenciado por uma OS existente sem passar pelos use cases.
- Ganho de performance imediato nas consultas mais comuns (buscar OS por cliente, item por insumo, veículo por proprietário) sem exigir migração de dados nem mudança de comportamento.
