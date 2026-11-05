package com.hotels.reserve_service.domain.model.state.impl;

import com.hotels.reserve_service.domain.exception.InvalidStateTransitionException;
import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.model.state.ReservationState;
import com.hotels.reserve_service.domain.model.state.ReservationStatus;

public class CompletedState implements ReservationState {

    @Override
    public ReservationStatus getStatus() {
        return ReservationStatus.COMPLETED;
    }

    @Override
    public Reserve confirm(Reserve reserve) {
        throw new InvalidStateTransitionException("La reserva ya fue completada.");
    }

    @Override
    public Reserve cancel(Reserve reserve, String reason) {
        throw new InvalidStateTransitionException("No se puede cancelar una reserva que ya finalizó.");
    }

    @Override
    public Reserve checkIn(Reserve reserve) {
        throw new InvalidStateTransitionException("La estadía de esta reserva ya terminó.");
    }

    @Override
    public Reserve complete(Reserve reserve) {
        throw new InvalidStateTransitionException("La reserva ya está completada.");
    }
}
