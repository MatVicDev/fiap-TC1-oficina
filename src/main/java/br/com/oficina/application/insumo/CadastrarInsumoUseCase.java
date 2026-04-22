package br.com.oficina.application.insumo;

import br.com.oficina.api.insumo.dto.InsumoInput;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CadastrarInsumoUseCase {
    private final InsumoRepository insumoRepository;

    public CadastrarInsumoUseCase(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    public UUID salvar(InsumoInput dados) {
        Insumo insumo = new Insumo(
                dados.descricao(),
                dados.precoBase(),
                dados.tipo(),
                dados.quantidadeInicial());

        Insumo salvo = insumoRepository.salvar(insumo);

        return salvo.getId();
    }
}
