package br.edu.utfpr.rest_contas.resources;



import br.edu.utfpr.rest_contas.domain.Conta;
import br.edu.utfpr.rest_contas.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/contas")
public class ContaResource {
    @Autowired
    private ContaService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Conta> listarContaPorId(@PathVariable Integer id) {
        Conta obj = service.buscarPorId(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Conta>> buscarTodos() {
        List<Conta> contas = service.buscarTodos();
        return ResponseEntity.ok().body(contas);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> inserirConta(@RequestBody Conta conta) {
        conta = service.inserirConta(conta);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(conta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizarConta(@RequestBody Conta conta) {
        Conta contaObj = service.atualizarConta(conta);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletaConta(@PathVariable Integer id) {
        service.deletaConta(id);
        return ResponseEntity.noContent().build();
    }
}
