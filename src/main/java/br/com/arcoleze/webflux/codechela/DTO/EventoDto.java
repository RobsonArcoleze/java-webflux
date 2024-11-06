package br.com.arcoleze.webflux.codechela.DTO;

import br.com.arcoleze.webflux.codechela.enums.TipoEvento;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link br.com.arcoleze.webflux.codechela.entities.Evento}
 */
public record EventoDto(Long id, TipoEvento tipo, String nome, LocalDate data,
                        String descricao) implements Serializable {
}