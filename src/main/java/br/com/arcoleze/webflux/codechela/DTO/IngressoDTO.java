package br.com.arcoleze.webflux.codechela.DTO;

import br.com.arcoleze.webflux.codechela.enums.TipoIngresso;

import java.io.Serializable;

public record IngressoDTO(Long id, Long eventoId, TipoIngresso tipo, Double valor, int total) implements Serializable {
}
