package com.hotels.reserve_service.domain.model.state.impl;

import com.hotels.reserve_service.domain.exception.InvalidStateTransitionException;
import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.model.state.ReservationState;
import com.hotels.reserve_service.domain.model.state.ReservationStatus;

public class ConfirmedState implements ReservationState {

    @Override
    public ReservationStatus getStatus() {
        return ReservationStatus.CONFIRMED;
    }

    @Override
    public Reserve confirm(Reserve reserve) {
        throw new InvalidStateTransitionException("La reserva ya se encuentra confirmada.");
    }

    @Override
    public Reserve cancel(Reserve reserve, String reason) {
        return reserve.toBuilder()
                .state(new CancelledState(reason))
                .build();
    }

    @Override
    public Reserve checkIn(Reserve reserve) {
        return reserve.toBuilder()
                .state(new CheckedInState())
                .build();
    }

    @Override
    public Reserve complete(Reserve reserve) {
        throw new InvalidStateTransitionException("No se puede completar una reserva sin haber realizado Check-In previo.");
    }
}
