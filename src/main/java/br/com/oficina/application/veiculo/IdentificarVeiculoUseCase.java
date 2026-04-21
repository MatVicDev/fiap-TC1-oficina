package br.com.oficina.application.veiculo;

import br.com.oficina.api.veiculo.dto.VeiculoOutput;
import br.com.oficina.domain.veiculo.Placa;
import br.com.oficina.domain.veiculo.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentificarVeiculoUseCase {
    private final VeiculoRepository repository;

    public IdentificarVeiculoUseCase(VeiculoRepository repository) {
        this.repository = repository;
    }

    public Optional<VeiculoOutput> identificar(String numeroPlaca) {
        Placa placa = new Placa(numeroPlaca);

        return repository.buscarPorPlaca(placa)
                .map(veiculo -> new VeiculoOutput(
                        veiculo.getPlaca().getNumero(),
                        veiculo.getMarca(),
                        veiculo.getModelo(),
                        veiculo.getCpfProprietario()
                ));
    }
}