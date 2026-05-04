package br.com.oficina.domain.ordemServico;

import br.com.oficina.domain.exception.DomainException;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "ordens_servicos")
public class OrdemServico {

    @Id
    private UUID id;

    private UUID clienteId;
    private UUID veiculoId;

    @Enumerated(EnumType.STRING)
    private StatusOS status;

    private String sintomaRelatado;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
    private List<ItemOS> itens = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orcamento_id")
    private Orcamento orcamento;

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

    public void iniciarDiagnostico() {
        validarTransicao(StatusOS.RECEBIDA);
        this.status = StatusOS.EM_DIAGNOSTICO;
    }

    public void finalizarDiagnostico() {
        validarTransicao(StatusOS.EM_DIAGNOSTICO);
        this.status = StatusOS.AGUARDANDO_APROVACAO;
    }

    public void aprovarOrcamento() {
        validarTransicao(StatusOS.AGUARDANDO_APROVACAO);
        this.status = StatusOS.EM_EXECUCAO;
        if (this.orcamento != null) {
            this.orcamento.aprovar();
        }
    }

    public void rejeitarOrcamento() {
        validarTransicao(StatusOS.AGUARDANDO_APROVACAO);
        this.status = StatusOS.FINALIZADA;
        if (this.orcamento != null) {
            this.orcamento.rejeitar();
        }
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
        if (this.status != status) {
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

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public UUID getVeiculoId() {
        return veiculoId;
    }

    public StatusOS getStatus() {
        return status;
    }

    public String getSintomaRelatado() {
        return sintomaRelatado;
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
