package com.hotels.reserve_service.infraestructure.adapter.out.persistence;

import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.port.out.IReserveRepositoryPort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository

public class ReserveRepositoryAdapter implements IReserveRepositoryPort {

    @Override
    public Mono<Reserve>  save(Reserve reserve) {

        Reserve savedReserve = new Reserve(
                "RES-" + System.currentTimeMillis(),
                reserve.getIdClient(),
                reserve.getIdRoom(),
                reserve.getStartDate(),
                reserve.getEndDate(),
                reserve.getTotalPrice(),
                reserve.getState()
        );

        return Mono.just(savedReserve);
    }

    @Override
    public Mono<Reserve> searchForId(String id) {
        return Mono.empty();
    }
}
