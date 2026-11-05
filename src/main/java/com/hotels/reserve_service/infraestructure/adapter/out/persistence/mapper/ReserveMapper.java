package com.hotels.reserve_service.infraestructure.adapter.out.persistence.mapper;

import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.model.state.ReservationState;
import com.hotels.reserve_service.domain.model.state.impl.CancelledState;
import com.hotels.reserve_service.domain.model.state.impl.CheckedInState;
import com.hotels.reserve_service.domain.model.state.impl.CompletedState;
import com.hotels.reserve_service.domain.model.state.impl.ConfirmedState;
import com.hotels.reserve_service.domain.model.state.impl.CreatedState;
import com.hotels.reserve_service.infraestructure.adapter.out.persistence.entity.ReserveEntity;
import org.springframework.stereotype.Component;

@Component

public class ReserveMapper {

    public ReserveEntity toEntity(Reserve domain) {
        if (domain == null) return null;

        return ReserveEntity.builder()
                .id(domain.getId())
                .idClient(domain.getIdClient())
                .idRoom(domain.getIdRoom())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .totalPrice(domain.getTotalPrice())
                .state(domain.getStateName()) // Extrae "CREATED", "CONFIRMED", etc.
                .build();
    }

    public Reserve toDomain(ReserveEntity entity) {
        if (entity == null) return null;

        return Reserve.builder()
                .id(entity.getId())
                .idClient(entity.getIdClient())
                .idRoom(entity.getIdRoom())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .totalPrice(entity.getTotalPrice())
                .state(mapStateFromText(entity.getState())) // Asigna la instancia concreta del patrón State
                .build();
    }

    private ReservationState mapStateFromText(String stateText) {
        if (stateText == null || stateText.isBlank()) {
            return new CreatedState();
        }

        return switch (stateText.toUpperCase()) {
            case "CONFIRMED" -> new ConfirmedState();
            case "CHECKED_IN" -> new CheckedInState();
            case "COMPLETED" -> new CompletedState();
            case "CANCELLED" -> new CancelledState("Recuperado desde BD");
            default -> new CreatedState();
        };
    }
}
