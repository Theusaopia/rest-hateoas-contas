package br.edu.utfpr.rest_contas.resources;

import br.edu.utfpr.rest_contas.domain.Pagamento;
import br.edu.utfpr.rest_contas.services.ContaService;
import br.edu.utfpr.rest_contas.services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/pagamentos")
public class PagamentoResource {
    @Autowired
    private PagamentoService service;

    @Autowired
    private ContaService contaService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pagamento> listarPagamentoPorId(@PathVariable Integer id) {
        Pagamento obj = service.buscarPorId(id);

        obj.add(
                linkTo(methodOn(PagamentoResource.class)
                        .listarPagamentoPorId(obj.getId())
                ).withSelfRel());

        obj.add(
                linkTo(methodOn(PagamentoResource.class)
                        .buscarTodos()
                ).withRel(IanaLinkRelations.COLLECTION));

        if(obj.getConta() != null) {
            obj.getConta().add(
                  linkTo(methodOn(ContaResource.class)
                                    .listarContaPorId(obj.getConta().getId()))
                            .withSelfRel()
            );

            obj.getConta().getCliente().add(linkTo(methodOn(ClienteResource.class).listarClientePorId(obj.getConta().getCliente().getId())).withSelfRel());
        }

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public CollectionModel<Pagamento> buscarTodos() {
        List<Pagamento> pagamentos = service.buscarTodos();

        for(Pagamento pag : pagamentos) {
            pag.add(linkTo(methodOn(PagamentoResource.class).listarPagamentoPorId(pag.getId())).withSelfRel());
            if(pag.getConta() != null) {
                pag.getConta().add(linkTo(methodOn(ContaResource.class).listarContaPorId(pag.getConta().getId())).withSelfRel());
                pag.getConta().getCliente().add(linkTo(methodOn(ClienteResource.class).listarClientePorId(pag.getConta().getCliente().getId())).withSelfRel());
            }
        }

        return CollectionModel.of(service.buscarTodos()).add(
                linkTo(methodOn(PagamentoResource.class)
                                .buscarTodos()).withSelfRel());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> inserirPagamento(@RequestBody Pagamento pagamento) {
        pagamento = service.inserirPagamento(pagamento);
        pagamento.getConta().setPagamento(pagamento);
        contaService.atualizarConta(pagamento.getConta());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizarPagamento(@RequestBody Pagamento pagamento) {
        Pagamento pagamentoObj = service.atualizarPagamento(pagamento);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletaPagamento(@PathVariable Integer id) {
        service.deletaPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
