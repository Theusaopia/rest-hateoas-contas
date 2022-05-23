package br.edu.utfpr.rest_contas.services;

import br.edu.utfpr.rest_contas.domain.Cliente;
import br.edu.utfpr.rest_contas.repositories.ClienteRepository;
import br.edu.utfpr.rest_contas.resources.exceptions.DataConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;

    public Cliente buscarPorId(Integer id) {
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElse(null);
    }

    public List<Cliente> buscarTodos() {
        return repo.findAll();
    }

    public Cliente inserirCliente(Cliente cliente) {
        cliente.setId(null);
        return repo.save(cliente);
    }

    public Cliente atualizarCliente(Cliente cliente) {
        Cliente novoCliente = buscarPorId(cliente.getId());
        atualizarContaObj(novoCliente, cliente);

        return repo.save(novoCliente);
    }

    public void atualizarContaObj(Cliente novoCliente, Cliente cliente) {
        novoCliente.setNome(cliente.getNome());
        novoCliente.setCpf(cliente.getCpf());
    }

    public void deletaCliente(Integer id) {
        buscarPorId(id);

        try{
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new DataConstraintException("Este objeto está linkado com outro, não pode ser deletado");
        }
    }
}
