package com.hotels.reserve_service.infraestructure.adapter.out.persistence;

import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.port.out.IReserveRepositoryPort;
import com.hotels.reserve_service.infraestructure.adapter.out.persistence.mapper.ReserveMapper;
import com.hotels.reserve_service.infraestructure.adapter.out.persistence.repository.ISpringDataReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ReserveRepositoryAdapter implements IReserveRepositoryPort {

    private final ISpringDataReserveRepository repository;
    private final ReserveMapper mapper;

    @Override
    public Mono<Reserve> save(Reserve reserve) {
        return Mono.just(reserve)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Reserve> searchForId(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
}
