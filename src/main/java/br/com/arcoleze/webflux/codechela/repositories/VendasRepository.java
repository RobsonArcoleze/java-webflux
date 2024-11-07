package br.com.arcoleze.webflux.codechela.repositories;

import br.com.arcoleze.webflux.codechela.entities.Venda;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendasRepository extends ReactiveCrudRepository<Venda, Long> {
}
