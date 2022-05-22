package br.edu.utfpr.rest_contas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Date;

@Entity @Getter
@Setter
@NoArgsConstructor
public class Pagamento extends RepresentationModel<Pagamento> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private Date dataPagamento;
    private String formaDePagamento;


    @OneToOne(mappedBy = "pagamento")
    private Conta conta;

    public Pagamento(Integer id, Date dataPagamento, String formaDePagamento, Conta conta) {
        this.id = id;
        this.dataPagamento = dataPagamento;
        this.formaDePagamento = formaDePagamento;
        this.conta = conta;
    }
}
