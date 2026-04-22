package br.com.oficina.domain.ordemServico;

import br.com.oficina.exception.DomainException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class OrdemServico {
    private UUID id;
    private UUID clienteId;
    private UUID veiculoId;
    private UUID mecanicoId;

    @Enumerated(EnumType.STRING)
    private StatusOS status;

    private String sintomaRelatado;
    private List<ItemOS> itens = new ArrayList<>();
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private LocalDateTime dataInicio;
    private LocalDateTime dataEntrega;

    public OrdemServico(UUID clienteId, UUID veiculoId, String sintomaRelatado) {
        this.id = UUID.randomUUID();
        this.clienteId = clienteId;
        this.veiculoId = veiculoId;
        this.status = StatusOS.RECEBIDA;
        this.sintomaRelatado = sintomaRelatado;
        this.dataInicio = LocalDateTime.now();
    }

    public void iniciarDiagnostico(UUID mecanicoId) {
        validarTransicao(StatusOS.RECEBIDA);
        this.status = StatusOS.EM_DIAGNOSTICO;
        this.mecanicoId = mecanicoId;
    }

    public void finalizarDiagnostico() {
        validarTransicao(StatusOS.FINALIZADA);
        this.status = StatusOS.AGUARDANDO_APROVACAO;
    }

    public void gerarOrcamentoParaAprovacao() {
        validarTransicao(StatusOS.EM_DIAGNOSTICO);

        if (itens.isEmpty()) {
            throw new IllegalStateException("Não é possível gerar orçamento sem itens/serviços.");
        }

        this.status = StatusOS.AGUARDANDO_APROVACAO;
    }

    public void aprovarOrcamento() {
        validarTransicao(StatusOS.AGUARDANDO_APROVACAO);
        this.status = StatusOS.EM_EXECUCAO;
    }

    public void rejeitarOrcamento() {
        validarTransicao(StatusOS.AGUARDANDO_APROVACAO);
        this.status = StatusOS.FINALIZADA;
    }

    public void finalizarServico() {
        validarTransicao(StatusOS.EM_EXECUCAO);
        this.status = StatusOS.FINALIZADA;
        this.dataEntrega = LocalDateTime.now();
    }

    public void registrarEntrega() {
        validarTransicao(StatusOS.FINALIZADA);
        this.status = StatusOS.ENTREGUE;
    }

    private void validarTransicao(StatusOS status) {
        if (this.status == status) {
            throw new IllegalStateException(String.format("Transição inválida: Não é possível realizar esta ação pois a OS está em estado %s.", this.status));
        }
    }

    public void adicionarItemOs(ItemOS item) {
        if (this.status != StatusOS.RECEBIDA && this.status != StatusOS.EM_DIAGNOSTICO) {
            throw new DomainException("Não é possível adicionar itens com a OS em estado de " + status);
        }

        this.itens.add(item);
        recalcularValorTotal();
    }

    private void recalcularValorTotal() {
        this.valorTotal = itens.stream()
                .map(ItemOS::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public UUID getId() {
        return id;
    }

    public StatusOS getStatus() {
        return status;
    }

    public List<ItemOS> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }
}
