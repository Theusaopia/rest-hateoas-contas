package br.edu.utfpr.rest_contas.resources;

import br.edu.utfpr.rest_contas.domain.Cliente;
import br.edu.utfpr.rest_contas.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
    @Autowired
    private ClienteService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> listarClientePorId(@PathVariable Integer id) {
        Cliente obj = service.buscarPorId(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> buscarTodos() {
        List<Cliente> clientes = service.buscarTodos();
        return ResponseEntity.ok().body(clientes);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> inserirCliente(@RequestBody Cliente cliente) {
        cliente = service.inserirCliente(cliente);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizarCliente(@RequestBody Cliente cliente) {
        Cliente clienteObj = service.atualizarCliente(cliente);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletaCliente(@PathVariable Integer id) {
        service.deletaCliente(id);
        return ResponseEntity.noContent().build();
    }
}
