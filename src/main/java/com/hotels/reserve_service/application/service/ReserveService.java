package com.hotels.reserve_service.application.service;

import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.port.in.ICreateReserveUseCase;
import com.hotels.reserve_service.domain.port.out.IReserveRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service

@RequiredArgsConstructor

public class ReserveService implements ICreateReserveUseCase {

    private final IReserveRepositoryPort reserveRepositoryPort;

    @Override
    public Mono<Reserve> create(Reserve reserve) {

        return reserveRepositoryPort.save(reserve);
    }
}
