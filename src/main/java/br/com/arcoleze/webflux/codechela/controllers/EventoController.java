package br.com.arcoleze.webflux.codechela.controllers;

import br.com.arcoleze.webflux.codechela.entities.Evento;
import br.com.arcoleze.webflux.codechela.services.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<Evento> buscaTodos(){
        return eventoService.findAll();
    }
}
