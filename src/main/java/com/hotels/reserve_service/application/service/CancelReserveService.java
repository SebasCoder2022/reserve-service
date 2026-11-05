package com.hotels.reserve_service.application.service;

import com.hotels.reserve_service.domain.exception.ReservationNotFoundException;
import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.port.in.ICancelReserveUseCase;
import com.hotels.reserve_service.domain.port.out.IReserveRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PrePostAnnotationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor

public class CancelReserveService implements ICancelReserveUseCase {

    private final IReserveRepositoryPort repositoryPort;

    @Override
    public Mono<Reserve> cancel(String id, String reason) {
        return repositoryPort.searchForId(id)
                .switchIfEmpty(Mono.error(new ReservationNotFoundException("Reserva con id " + id + " no encontrada.")))
                .map(reserve -> reserve.cancel(reason))
                .flatMap(repositoryPort::save);
    }
}
