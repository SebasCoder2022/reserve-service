package com.hotels.reserve_service.application.service;

import com.hotels.reserve_service.domain.exception.ReservationNotFoundException;
import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.port.in.ICheckInReserveUseCase;
import com.hotels.reserve_service.domain.port.out.IReserveRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor

public class CheckInReserveService implements ICheckInReserveUseCase {

    private final IReserveRepositoryPort repositoryPort;

    @Override
    public Mono<Reserve> checkIn(String id) {
        return repositoryPort.searchForId(id)
                .switchIfEmpty(Mono.error(new ReservationNotFoundException("Reserva con id " + id + " no encontrada.")))
                .map(Reserve::checkIn)
                .flatMap(repositoryPort::save);
    }

}
