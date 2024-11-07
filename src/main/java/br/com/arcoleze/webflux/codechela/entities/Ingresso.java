package br.com.arcoleze.webflux.codechela.entities;

import br.com.arcoleze.webflux.codechela.enums.TipoIngresso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Service;

@Table("ingressos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ingresso {

    @Id
    private Long id;
    private Long eventoId;
    private TipoIngresso tipo;
    private Double valor;
    private int total;
}
