package br.com.arcoleze.webflux.codechela.repositories;

import br.com.arcoleze.webflux.codechela.entities.Evento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {
}
