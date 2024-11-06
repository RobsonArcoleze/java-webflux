package br.com.arcoleze.webflux.codechela.services;

import br.com.arcoleze.webflux.codechela.entities.Evento;
import br.com.arcoleze.webflux.codechela.repositories.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class EventoService {


    private final EventoRepository eventoRepository;

    public Flux<Evento> findAll() {

        return eventoRepository.findAll();
    }
}

