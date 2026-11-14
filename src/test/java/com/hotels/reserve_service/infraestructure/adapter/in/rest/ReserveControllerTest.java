package com.hotels.reserve_service.infraestructure.adapter.in.rest;

import com.hotels.reserve_service.domain.exception.InvalidStateTransitionException;
import com.hotels.reserve_service.domain.exception.ReservationNotFoundException;
import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.model.state.impl.CheckedInState;
import com.hotels.reserve_service.domain.port.in.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@WebFluxTest(controllers = {ReserveController.class, GlobalExceptionHandler.class})
@WithMockUser
class ReserveControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    // Simulamos todos los casos de uso inyectados en ReserveController
    @MockBean
    private ICreateReserveUseCase createReserveUseCase;

    @MockBean
    private IConfirmReserveUseCase confirmReserveUseCase;

    @MockBean
    private ICancelReserveUseCase cancelReserveUseCase;

    @MockBean
    private ICheckInReserveUseCase checkInReserveUseCase;

    @MockBean
    private ICompleteReserveUseCase completeReserveUseCase;

    @Test
    @DisplayName("PATCH /check-in debe retornar 200 OK cuando el check-in es válido")
    void shouldReturn200WhenCheckInSucceeds() {
        String reserveId = "RES-500";
        Reserve checkedInReserve = Reserve.builder()
                .id(reserveId)
                .state(new CheckedInState())
                .build();

        when(checkInReserveUseCase.checkIn(reserveId)).thenReturn(Mono.just(checkedInReserve));

        webTestClient
                .mutateWith(csrf())
                .patch()
                .uri("/api/v1/reserves/{id}/check-in", reserveId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(reserveId);
    }

    @Test
    @DisplayName("PATCH /cancel debe retornar 404 Not Found cuando la reserva no existe")
    void shouldReturn404WhenReserveNotFound() {
        String reserveId = "RES-404";
        when(cancelReserveUseCase.cancel(eq(reserveId), any()))
                .thenReturn(Mono.error(new ReservationNotFoundException("Reserva con id " + reserveId + " no encontrada.")));

        webTestClient
                .mutateWith(csrf())
                .patch()
                .uri("/api/v1/reserves/{id}/cancel", reserveId)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("Reserva No Encontrada");
    }

    @Test
    @DisplayName("PATCH /complete debe retornar 400 Bad Request cuando la transición es inválida")
    void shouldReturn400WhenTransitionIsInvalid() {
        String reserveId = "RES-400";
        when(completeReserveUseCase.Complete(reserveId))
                .thenReturn(Mono.error(new InvalidStateTransitionException("No se puede completar una reserva cancelada.")));

        webTestClient
                .mutateWith(csrf())
                .patch()
                .uri("/api/v1/reserves/{id}/complete", reserveId)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.error").isEqualTo("Transición de Estado Inválida");
    }
}
