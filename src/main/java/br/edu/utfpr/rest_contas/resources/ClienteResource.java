package br.edu.utfpr.rest_contas.resources;

import br.edu.utfpr.rest_contas.domain.Cliente;
import br.edu.utfpr.rest_contas.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
    @Autowired
    private ClienteService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> listarClientePorId(@PathVariable Integer id) {
        Cliente obj = service.buscarPorId(id);
        obj.add(linkTo(methodOn(ClienteResource.class).listarClientePorId(obj.getId())).withSelfRel());
        obj.add(linkTo(methodOn(ClienteResource.class).buscarTodos()).withRel("Clientes"));
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> buscarTodos() {
        List<Cliente> clientes = service.buscarTodos();
        for (Cliente cliente : clientes) {
            cliente.add(linkTo(methodOn(ClienteResource.class).listarClientePorId(cliente.getId())).withSelfRel());

        }
        return ResponseEntity.ok().body(clientes);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Cliente> inserirCliente(@RequestBody Cliente cliente) {
        cliente = service.inserirCliente(cliente);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();

        return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente cliente) {
        Cliente clienteObj = service.atualizarCliente(cliente);
        return new ResponseEntity<Cliente>(clienteObj,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletaCliente(@PathVariable Integer id) {
        service.deletaCliente(id);
        return ResponseEntity.noContent().build();
    }
}
