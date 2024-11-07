package br.com.arcoleze.webflux.codechela.DTO;

import java.io.Serializable;

/**
 * DTO for {@link br.com.arcoleze.webflux.codechela.entities.Venda}
 */
public record VendaDto(Long id, Long ingressoId, int total) implements Serializable {
}