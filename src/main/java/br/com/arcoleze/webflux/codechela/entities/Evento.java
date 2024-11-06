package br.com.arcoleze.webflux.codechela.entities;

import br.com.arcoleze.webflux.codechela.enums.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;

@Table("eventos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Evento implements Serializable {

    @Id
    private Long id;
    private TipoEvento tipo;
    private String nome;
    private LocalDate data;
    private String descricao;
}
