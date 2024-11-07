package br.com.arcoleze.webflux.codechela.services;

import br.com.arcoleze.webflux.codechela.DTO.CompraDTO;
import br.com.arcoleze.webflux.codechela.DTO.IngressoDTO;
import br.com.arcoleze.webflux.codechela.entities.Ingresso;
import br.com.arcoleze.webflux.codechela.entities.Venda;
import br.com.arcoleze.webflux.codechela.repositories.IngressoRepository;
import br.com.arcoleze.webflux.codechela.repositories.VendasRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private VendasRepository vendasRepository;

    @Transactional(readOnly = true)
    public Flux<IngressoDTO> findAll() {
        return ingressoRepository.findAll().map(ig -> new IngressoDTO(
                ig.getId(),
                ig.getEventoId(),
                ig.getTipo(),
                ig.getValor(),
                ig.getTotal()
        ));
    }

    @Transactional(readOnly = true)
    public Mono<IngressoDTO> findById(Long id) {
        return ingressoRepository.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do ingresso não encontrado!")))
                .flatMap(ingresso -> Mono.just(new IngressoDTO(
                        ingresso.getId(),
                        ingresso.getEventoId(),
                        ingresso.getTipo(),
                        ingresso.getValor(),
                        ingresso.getTotal()
                )));
    }

    @Transactional
    public Mono<IngressoDTO> save(IngressoDTO dto) {

        Ingresso ingresso = new Ingresso(dto.id(), dto.eventoId(), dto.tipo(), dto.valor(), dto.total());

        return ingressoRepository.save(ingresso).map(ig -> new IngressoDTO(
                ig.getId(),
                ig.getEventoId(),
                ig.getTipo(),
                ig.getValor(),
                ig.getTotal()
        ));
    }

    @Transactional
    public Mono<Void> delete(Long id) {
        return ingressoRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado")))
                .flatMap(ingressoRepository::delete);
    }

    @Transactional
    public Mono<IngressoDTO> update(IngressoDTO dto) {
        Ingresso ingresso = new Ingresso(dto.id(), dto.eventoId(), dto.tipo(), dto.valor(), dto.total());
        return ingressoRepository.save(ingresso).map(ig -> new IngressoDTO(
                ig.getId(),
                ig.getEventoId(),
                ig.getTipo(),
                ig.getValor(),
                ig.getTotal()
        ));
    }

//    @Transactional
//    public Mono<IngressoDTO> buyTicket(CompraDTO dto) {
//
//        return ingressoRepository.findById(dto.ingressoId())
//                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "ID do ingresso não encontrado")))
//                .flatMap(it -> {
//                    Venda venda = new Venda();
//                    venda.setIngressoId(it.getId());
//                    venda.setTotal(it.getTotal());
//                    return vendasRepository.save(venda)
//                            .then(Mono.defer(() ->
//                                    it.setTotal(it.getTotal() - dto.total())
//                                    ingressoRepository.save(it)
//                            ))
//                            .map(ig -> new IngressoDTO(
//                                    ig.getId(),
//                                    ig.getEventoId(),
//                                    ig.getTipo(),
//                                    ig.getValor(),
//                                    ig.getTotal()
//                            ));
//                });
//    }

    @Transactional
    public Mono<IngressoDTO> buyTicket(CompraDTO dto) {
        return ingressoRepository.findById(dto.ingressoId())
                .flatMap(ingresso -> {
                    Venda venda = new Venda();
                    venda.setIngressoId(ingresso.getId());
                    venda.setTotal(dto.total());
                    return vendasRepository.save(venda).then(Mono.defer(() -> {
                        ingresso.setTotal(ingresso.getTotal() - dto.total());
                        return ingressoRepository.save(ingresso);
                    }));
                }).map(ig -> new IngressoDTO(
                        ig.getId(),
                        ig.getEventoId(),
                        ig.getTipo(),
                        ig.getValor(),
                        ig.getTotal()
                ));
    }

}
