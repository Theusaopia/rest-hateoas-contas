package br.edu.utfpr.rest_contas.repositories;

import br.edu.utfpr.rest_contas.domain.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

}
