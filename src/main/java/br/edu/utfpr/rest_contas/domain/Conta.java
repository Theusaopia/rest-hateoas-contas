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
public class Conta extends RepresentationModel<Conta> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double valor;
    private Date vencimento;

    @OneToOne
    private Cliente cliente;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "pagamento_id", referencedColumnName = "id")
    private Pagamento pagamento;

    public Conta(Integer id, double valor, Date vencimento, Cliente cliente) {
        this.id = id;
        this.valor = valor;
        this.vencimento = vencimento;
        this.cliente = cliente;
    }
}
