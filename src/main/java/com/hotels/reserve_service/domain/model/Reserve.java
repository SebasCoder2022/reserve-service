package com.hotels.reserve_service.domain.model;

import com.hotels.reserve_service.domain.model.state.ReservationState;
import com.hotels.reserve_service.domain.model.state.impl.CreatedState;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Reserve {

    private String id;
    private String idClient;
    private String idRoom;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double totalPrice;

    @Builder.Default
    private ReservationState state = new CreatedState();

    public Reserve confirm() {
        return this.state.confirm(this);
    }

    public Reserve cancel(String reason) {
        return this.state.cancel(this, reason);
    }

    public Reserve checkIn() {
        return this.state.checkIn(this);
    }

    public Reserve complete() {
        return this.state.complete(this);
    }

    public String getStateName() {
        return this.state != null ? this.state.getStatus().name() : null;
    }

}
