package br.edu.utfpr.rest_contas.resources;

import br.edu.utfpr.rest_contas.domain.Cliente;
import br.edu.utfpr.rest_contas.domain.Conta;
import br.edu.utfpr.rest_contas.domain.Pagamento;
import br.edu.utfpr.rest_contas.repositories.ClienteRepository;
import br.edu.utfpr.rest_contas.repositories.ContaRepository;
import br.edu.utfpr.rest_contas.repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class DBService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    PagamentoRepository pagamentoRepository;

    public void iniciarBancoDeDados() {
        Cliente cliente = new Cliente(null, "Bruvanessa", "9999999999");
        Cliente cliente2 = new Cliente(null, "Rodrigo Faro", "9999999999");
        Cliente cliente3 = new Cliente(null, "Paulo Kogos", "9999999999");
        Cliente cliente4 = new Cliente(null, "Eurico miranda", "9999999999");

        clienteRepository.saveAll(Arrays.asList(cliente, cliente2, cliente3, cliente4));

        Conta c1 = new Conta(null, 10.00, new Date(System.currentTimeMillis()), cliente);
        Conta c2 = new Conta(null, 16.78, new Date(System.currentTimeMillis()), cliente4);
        Conta c3 = new Conta(null, 245.10, new Date(System.currentTimeMillis()), cliente2);

        contaRepository.saveAll(Arrays.asList(c1, c2, c3));

        Pagamento p1 = new Pagamento(null, new Date(System.currentTimeMillis()), "Dinheiro", c1);
        Pagamento p2 = new Pagamento(null, new Date(System.currentTimeMillis()), "Cartao", c3);

        pagamentoRepository.saveAll(Arrays.asList(p1, p2));
    }
}
