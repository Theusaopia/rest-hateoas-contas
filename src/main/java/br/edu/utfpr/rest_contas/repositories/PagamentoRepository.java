package br.edu.utfpr.rest_contas.repositories;

import br.edu.utfpr.rest_contas.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
