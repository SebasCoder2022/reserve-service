package com.hotels.reserve_service.application.service;

import com.hotels.reserve_service.domain.exception.InvalidStateTransitionException;
import com.hotels.reserve_service.domain.exception.ReservationNotFoundException;
import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.model.state.ReservationStatus;
import com.hotels.reserve_service.domain.model.state.impl.CompletedState;
import com.hotels.reserve_service.domain.model.state.impl.CreatedState;
import com.hotels.reserve_service.domain.port.out.IReserveRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelReserveServiceTest {

    @Mock
    private IReserveRepositoryPort repositoryPort;

    @InjectMocks
    private CancelReserveService cancelReserveService;

    @Test
    @DisplayName("Escenario 1: Cancelar reserva CREADA exitosamente")
    void shouldCancelReserveSuccessfullyWhenStateIsCreated() {
        // GIVEN: Una reserva existente en estado CREATED
        String reserveId = "RES-100";
        String reason = "Cambio de itinerario";

        Reserve mockReserve = Reserve.builder()
                .id(reserveId)
                .idClient("CLI-01")
                .idRoom("ROOM-10")
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(3))
                .totalPrice(150.0)
                .state(new CreatedState())
                .build();

        when(repositoryPort.searchForId(reserveId)).thenReturn(Mono.just(mockReserve));
        when(repositoryPort.save(any(Reserve.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // WHEN: Invocamos la cancelación
        Mono<Reserve> result = cancelReserveService.cancel(reserveId, reason);

        // THEN: StepVerifier valida que el nuevo estado sea CANCELLED
        StepVerifier.create(result)
                .expectNextMatches(saved ->
                        saved.getId().equals(reserveId) &&
                                saved.getState().getStatus() == ReservationStatus.CANCELLED
                )
                .verifyComplete();

        verify(repositoryPort, times(1)).searchForId(reserveId);
        verify(repositoryPort, times(1)).save(any(Reserve.class));
    }

    @Test
    @DisplayName("Escenario 2: Error cuando la reserva no existe en BD")
    void shouldThrowExceptionWhenReserveNotFound() {
        // GIVEN: El ID no existe en la BD
        String reserveId = "RES-INEXISTENTE";
        when(repositoryPort.searchForId(reserveId)).thenReturn(Mono.empty());

        // WHEN: Intentamos cancelar
        Mono<Reserve> result = cancelReserveService.cancel(reserveId, "No importa el motivo");

        // THEN: StepVerifier comprueba que se emite la excepción de no encontrada
        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof ReservationNotFoundException &&
                                error.getMessage().contains("no encontrada")
                )
                .verify();

        verify(repositoryPort, times(1)).searchForId(reserveId);
        verify(repositoryPort, never()).save(any(Reserve.class));
    }

    @Test
    @DisplayName("Escenario 3: Error cuando la regla de negocio prohíbe cancelar")
    void shouldThrowExceptionWhenReserveAlreadyCompleted() {
        // GIVEN: La reserva ya finalizó (estado COMPLETED)
        String reserveId = "RES-200";
        Reserve completedReserve = Reserve.builder()
                .id(reserveId)
                .state(new CompletedState())
                .build();

        when(repositoryPort.searchForId(reserveId)).thenReturn(Mono.just(completedReserve));

        // WHEN: Intentamos cancelarla
        Mono<Reserve> result = cancelReserveService.cancel(reserveId, "Motivo tardío");

        // THEN: El patrón State frena la operación y emite InvalidStateTransitionException
        StepVerifier.create(result)
                .expectError(InvalidStateTransitionException.class)
                .verify();

        verify(repositoryPort, never()).save(any(Reserve.class));
    }
}

