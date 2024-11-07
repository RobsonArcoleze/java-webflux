package br.com.arcoleze.webflux.codechela.controllers;

import br.com.arcoleze.webflux.codechela.DTO.CompraDTO;
import br.com.arcoleze.webflux.codechela.DTO.IngressoDTO;
import br.com.arcoleze.webflux.codechela.services.IngressoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping(value = "/ingresso")
public class IngressoController {

    private final IngressoService ingressoService;
    private final Sinks.Many<IngressoDTO> ingressoSink;

    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
        this.ingressoSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping
    private Flux<IngressoDTO> findAll() {
        return this.ingressoService.findAll();
    }

    @GetMapping(value = "/{id}")
    private Mono<IngressoDTO> findById(@PathVariable Long id) {
        return this.ingressoService.findById(id);
    }

    @PostMapping
    private Mono<IngressoDTO> save(@RequestBody IngressoDTO dto) {

        return this.ingressoService.save(dto);
    }

    @PutMapping
    private Mono<IngressoDTO> update(@RequestBody IngressoDTO dto) {
        return this.ingressoService.update(dto);
    }

    @DeleteMapping(value = "/{id}")
    private Mono<Void> delete(@PathVariable Long id) {
        return this.ingressoService.delete(id);
    }

    @PostMapping(value = "/compra")
    private Mono<IngressoDTO> buyTicket(@RequestBody CompraDTO dto) {
        return this.ingressoService.buyTicket(dto).doOnSuccess(ingressoSink::tryEmitNext);
    }

    @GetMapping(value = "/{id}/disponivel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<IngressoDTO> totalAvailable(@PathVariable Long id) {
        return Flux.merge(this.ingressoService.findById(id), ingressoSink.asFlux()).delayElements(Duration.ofSeconds(4));
    }
}
