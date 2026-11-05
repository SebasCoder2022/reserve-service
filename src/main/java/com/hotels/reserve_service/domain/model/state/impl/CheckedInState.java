package com.hotels.reserve_service.domain.model.state.impl;

import com.hotels.reserve_service.domain.exception.InvalidStateTransitionException;
import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.model.state.ReservationState;
import com.hotels.reserve_service.domain.model.state.ReservationStatus;

public class CheckedInState implements ReservationState {

    @Override
    public ReservationStatus getStatus() {
        return ReservationStatus.CHECKED_IN;
    }

    @Override
    public Reserve confirm(Reserve reserve) {
        throw new InvalidStateTransitionException("La reserva ya pasó la confirmación y está en estadía.");
    }

    @Override
    public Reserve cancel(Reserve reserve, String reason) {
        throw new InvalidStateTransitionException("No se puede cancelar una reserva con Check-In activo.");
    }

    @Override
    public Reserve checkIn(Reserve reserve) {
        throw new InvalidStateTransitionException("El huésped ya realizó Check-In.");
    }

    @Override
    public Reserve complete(Reserve reserve) {
        return reserve.toBuilder()
                .state(new CompletedState())
                .build();
    }
}
