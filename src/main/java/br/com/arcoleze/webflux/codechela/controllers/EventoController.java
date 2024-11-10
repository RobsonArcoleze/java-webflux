package br.com.arcoleze.webflux.codechela.controllers;

import br.com.arcoleze.webflux.codechela.DTO.EventoDto;
import br.com.arcoleze.webflux.codechela.entities.Evento;
import br.com.arcoleze.webflux.codechela.services.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping(value = "/eventos")
public class EventoController {

    private final EventoService eventoService;
    private final Sinks.Many<EventoDto> eventosSink;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
        this.eventosSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping
    private Flux<EventoDto> findall(){
        return eventoService.findAll();
    }

    @GetMapping(value = "/categoria/{tipo}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<EventoDto> findbyCategoria(@PathVariable String tipo){
        return Flux.merge(eventoService.findByCategory(tipo), eventosSink.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }

    @GetMapping(value = "/{id}")
    private Mono<EventoDto> findById(@PathVariable Long id){
        return eventoService.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<EventoDto> save(@RequestBody EventoDto eventoDto){
        return eventoService.save(eventoDto).doOnSuccess(eventosSink::tryEmitNext);
    }

    @PutMapping
    private Mono<EventoDto> update(@RequestBody EventoDto eventoDto){
        return eventoService.update(eventoDto).doOnSuccess(eventosSink::tryEmitNext);
    }

    @DeleteMapping(value = "/{id}")
    private Mono<Void> delete(@PathVariable Long id){
        return eventoService.deleteById(id);
    }
}
