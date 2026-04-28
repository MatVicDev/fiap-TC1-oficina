package br.com.oficina.application.insumo;

import br.com.oficina.api.insumo.dto.CadastrarInsumoRequest;
import br.com.oficina.api.insumo.dto.InsumoResponse;
import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarInsumoUseCase {
    private final InsumoRepository insumoRepository;
    private final EstoqueRepository estoqueRepository;

    @Transactional
    public InsumoResponse executar(CadastrarInsumoRequest request) {
        Insumo insumo = new Insumo(
                request.nome(),
                request.descricao(),
                request.precoBase(),
                request.tipo());

        insumoRepository.salvar(insumo);

        Estoque estoque = new Estoque(insumo.getId(), request.quantidadeInicial());

        estoqueRepository.salvar(estoque);

        return InsumoMapper.toResponse(insumo, estoque);
    }
}
