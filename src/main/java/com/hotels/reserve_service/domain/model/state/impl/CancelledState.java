package com.hotels.reserve_service.domain.model.state.impl;

import com.hotels.reserve_service.domain.exception.InvalidStateTransitionException;
import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.model.state.ReservationState;
import com.hotels.reserve_service.domain.model.state.ReservationStatus;

public class CancelledState implements ReservationState {

    private final String cancellationReason;

    public CancelledState(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    @Override
    public ReservationStatus getStatus() {
        return ReservationStatus.CANCELLED;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    @Override
    public Reserve confirm(Reserve reserve) {
        throw new InvalidStateTransitionException("No se puede confirmar una reserva CANCELADA.");
    }

    @Override
    public Reserve cancel(Reserve reserve, String reason) {
        throw new InvalidStateTransitionException("La reserva ya se encuentra cancelada.");
    }

    @Override
    public Reserve checkIn(Reserve reserve) {
        throw new InvalidStateTransitionException("No es posible realizar Check-In en una reserva cancelada.");
    }

    @Override
    public Reserve complete(Reserve reserve) {
        throw new InvalidStateTransitionException("Operación no permitida: reserva cancelada.");
    }
}
