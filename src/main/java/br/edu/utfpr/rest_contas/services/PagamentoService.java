package br.edu.utfpr.rest_contas.services;

import br.edu.utfpr.rest_contas.domain.Pagamento;
import br.edu.utfpr.rest_contas.repositories.PagamentoRepository;
import br.edu.utfpr.rest_contas.resources.exceptions.DataConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository repo;

    public Pagamento buscarPorId(Integer id) {
        Optional<Pagamento> obj = repo.findById(id);
        return obj.orElse(null);
    }

    public List<Pagamento> buscarTodos() {
        return repo.findAll();
    }

    public Pagamento inserirPagamento(Pagamento pagamento) {
        pagamento.setId(null);
        return repo.save(pagamento);
    }

    public Pagamento atualizarPagamento(Pagamento pagamento) {
        Pagamento novaConta = buscarPorId(pagamento.getId());
        atualizarPagamentoObj(novaConta, pagamento);

        return repo.save(novaConta);
    }

    public void atualizarPagamentoObj(Pagamento novoPagamento, Pagamento pagamento) {
        novoPagamento.setDataPagamento(pagamento.getDataPagamento());
        novoPagamento.setFormaDePagamento(pagamento.getFormaDePagamento());
        novoPagamento.setConta(pagamento.getConta());
    }

    public void deletaPagamento(Integer id) {
        buscarPorId(id);

        try{
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new DataConstraintException("Este objeto está linkado com outro, não pode ser deletado");
        }
    }
}
