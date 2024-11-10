package br.com.arcoleze.webflux.codechela.controllers;

import br.com.arcoleze.webflux.codechela.DTO.EventoDto;
import br.com.arcoleze.webflux.codechela.enums.TipoEvento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventoControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void saveShouldSaveEventWhenValidEvent() {
        EventoDto dto = new EventoDto(null, TipoEvento.SHOW, "Musical de natal", LocalDate.parse("2024-12-25"), "A maior festa do ano!");

        webClient.post()
                .uri("/eventos")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(EventoDto.class)
                .value(resp -> {
                    assertNotNull(resp.id());
                    assertEquals(dto.data(), resp.data());
                    assertEquals(dto.tipo(), resp.tipo());
                    assertEquals(dto.nome(), resp.nome());
                    assertEquals(dto.descricao(), resp.descricao());
                });
    }

    @Test
    public void getAllShouldReturnAllEvents() {
        webClient.get()
                .uri("/eventos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EventoDto.class);
    }

    @Test
    public void getByIdShouldReturnEventWhenValidId() {
        webClient.get()
                .uri("/eventos/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventoDto.class);
    }

    @Test
    public void getByIdShouldReturnNotFoundWhenInvalidId() {
        webClient.get()
                .uri("/eventos/{id}", 1000)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(EventoDto.class);
    }

    @Test
    public void deleteShouldDeleteEventWhenValidId() {
        webClient.delete()
                .uri("/eventos/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }
}
