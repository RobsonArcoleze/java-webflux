package br.com.arcoleze.webflux.codechela.repositories;

import br.com.arcoleze.webflux.codechela.entities.Ingresso;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngressoRepository extends ReactiveCrudRepository<Ingresso, Long> {
}
