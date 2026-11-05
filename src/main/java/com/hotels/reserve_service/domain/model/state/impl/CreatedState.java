package com.hotels.reserve_service.domain.model.state.impl;

import com.hotels.reserve_service.domain.exception.InvalidStateTransitionException;
import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.model.state.ReservationState;
import com.hotels.reserve_service.domain.model.state.ReservationStatus;

public class CreatedState implements ReservationState {

    @Override
    public ReservationStatus getStatus() {
        return ReservationStatus.CREATED;
    }

    @Override
    public Reserve confirm(Reserve reserve) {
        return reserve.toBuilder()
                .state(new ConfirmedState())
                .build();
    }

    @Override
    public Reserve cancel(Reserve reserve, String reason) {
        return reserve.toBuilder()
                .state(new CancelledState(reason))
                .build();
    }

    @Override
    public Reserve checkIn(Reserve reserve) {
        throw new InvalidStateTransitionException("No se puede hacer Check-In en una reserva que aún no está CONFIRMADA.");
    }

    @Override
    public Reserve complete(Reserve reserve) {
        throw new InvalidStateTransitionException("No se puede completar una reserva en estado CREADA.");
    }
}
