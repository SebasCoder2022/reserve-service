package com.hotels.reserve_service.domain.model.state;

import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.model.Reserve;

public interface ReservationState {

    ReservationStatus getStatus();

    Reserve confirm(Reserve reserve);

    Reserve cancel(Reserve reserve, String reason);

    Reserve checkIn(Reserve reserve);

    Reserve complete(Reserve reserve);
}
