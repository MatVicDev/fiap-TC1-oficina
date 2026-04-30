package br.com.oficina.infrastructure.persistence.veiculo;

import br.com.oficina.domain.cliente.Cpf;
import br.com.oficina.domain.veiculo.Placa;
import br.com.oficina.domain.veiculo.Veiculo;
import br.com.oficina.domain.veiculo.VeiculoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VeiculoRepositoryJpa extends JpaRepository<Veiculo, UUID>, VeiculoRepository {

    @Override
    default Veiculo salvar(Veiculo veiculo) {
        return save(veiculo);
    }

    @Override
    default Optional<Veiculo> buscarPorPlaca(Placa placa) {
        return findByPlaca(placa);
    }

    @Override
    default Optional<Veiculo> buscarPorId(UUID id) {
        return findById(id);
    }

    Optional<Veiculo> findByPlaca(Placa placa);

    @Override
    default List<Veiculo> listarVeiculos() {
        return findAll();
    }

    @Override
    default List<Veiculo> listarPorCpfProprietario(Cpf cpf) {
        return findByCpfProprietario(cpf.getNumero());
    }

    List<Veiculo> findByCpfProprietario(String cpf);

    @Override
    default boolean existePorPlaca(Placa placa) {
        return existsByPlaca(placa);
    }

    boolean existsByPlaca(Placa placa);

    @Override
    default void excluir(UUID id) {
        deleteById(id);
    }
}
