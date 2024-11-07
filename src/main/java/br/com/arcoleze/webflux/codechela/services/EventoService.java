package br.com.arcoleze.webflux.codechela.services;

import br.com.arcoleze.webflux.codechela.DTO.EventoDto;
import br.com.arcoleze.webflux.codechela.entities.Evento;
import br.com.arcoleze.webflux.codechela.enums.TipoEvento;
import br.com.arcoleze.webflux.codechela.repositories.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    @Transactional(readOnly = true)
    public Flux<EventoDto> findAll() {
        return eventoRepository.findAll().map(evento -> new EventoDto(
                evento.getId(),
                evento.getTipo(),
                evento.getNome(),
                evento.getData(),
                evento.getDescricao()
        ));
    }

    @Transactional(readOnly = true)
    public Mono<EventoDto> findById(Long id) {
        return eventoRepository.findById(id).map(evento -> new EventoDto(
                evento.getId(),
                evento.getTipo(),
                evento.getNome(),
                evento.getData(),
                evento.getDescricao()
        ));
    }

    @Transactional
    public Mono<EventoDto> save(EventoDto eventoDto) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(eventoDto, evento);
        return eventoRepository.save(evento).map(event -> new EventoDto(
                event.getId(),
                event.getTipo(),
                event.getNome(),
                event.getData(),
                event.getDescricao()
        ));
    }

    @Transactional
    public Mono<Void> deleteById(Long id) {
        return eventoRepository.deleteById(id);
    }

    @Transactional
    public Mono<EventoDto> update(EventoDto eventoDto) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(eventoDto, evento);

        return eventoRepository.findById(evento.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "ID do evento não econtrado")))
                .flatMap(eventoRepository::save).map(event -> new EventoDto(
                        event.getId(),
                        event.getTipo(),
                        event.getNome(),
                        event.getData(),
                        event.getDescricao()
                ));
    }

    @Transactional(readOnly = true)
    public Flux<EventoDto> findByCategory(String tipo) {
        TipoEvento type = TipoEvento.valueOf(tipo.toUpperCase());
        return eventoRepository.findByTipo(type).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo não encontrado")))
                .map(evento -> new EventoDto(
                        evento.getId(),
                        evento.getTipo(),
                        evento.getNome(),
                        evento.getData(),
                        evento.getDescricao()
                ));
    }
}

