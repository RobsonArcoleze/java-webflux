package br.com.arcoleze.webflux.codechela.repositories;

import br.com.arcoleze.webflux.codechela.DTO.EventoDto;
import br.com.arcoleze.webflux.codechela.entities.Evento;
import br.com.arcoleze.webflux.codechela.enums.TipoEvento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {
    Flux<Evento> findByTipo(TipoEvento type);
}
