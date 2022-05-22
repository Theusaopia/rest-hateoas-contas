package br.edu.utfpr.rest_contas.services;

import br.edu.utfpr.rest_contas.domain.Conta;
import br.edu.utfpr.rest_contas.repositories.ContaRepository;
import br.edu.utfpr.rest_contas.resources.exceptions.DataConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {
    @Autowired
    private ContaRepository repo;

    public Conta buscarPorId(Integer id) {
        Optional<Conta> obj = repo.findById(id);
        return obj.orElse(null);
    }

    public List<Conta> buscarTodos() {
        return repo.findAll();
    }

    public Conta inserirConta(Conta conta) {
        conta.setId(null);
        return repo.save(conta);
    }

    public Conta atualizarConta(Conta conta) {
        Conta novaConta = buscarPorId(conta.getId());
        atualizarContaObj(novaConta, conta);

        return repo.save(novaConta);
    }

    public void atualizarContaObj(Conta novaConta, Conta conta) {
        novaConta.setCliente(conta.getCliente());
        novaConta.setPagamento(conta.getPagamento());
        novaConta.setValor(conta.getValor());
        novaConta.setVencimento(conta.getVencimento());
    }

    public void deletaConta(Integer id) {
        buscarPorId(id);

        try{
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new DataConstraintException("Este objeto está linkado com outro, não pode ser deletado");
        }
    }
}
