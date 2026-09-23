package com.hotels.reserve_service.infraestructure.adapter.out.persistence;

import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.port.out.IReserveRepositoryPort;
<<<<<<< HEAD
import com.hotels.reserve_service.infraestructure.adapter.out.persistence.entity.ReserveEntity;
import com.hotels.reserve_service.infraestructure.adapter.out.persistence.repository.ISpringDataReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor

public class ReserveRepositoryAdapter implements IReserveRepositoryPort {

    private final ISpringDataReserveRepository repository;

    @Override
    public Mono<Reserve> save(Reserve reserve) {
        return repository.save(toEntity(reserve))
                .map(this::toDomain);
=======
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
>>>>>>> 6b50b80c95c19ffc3dd2508aca6638645bf2d412
    }

    @Override
    public Mono<Reserve> searchForId(String id) {
<<<<<<< HEAD
        return repository.findById(id)
                .map(this::toDomain);
    }

    private ReserveEntity toEntity(Reserve domain) {
        return ReserveEntity.builder()
                .id(domain.getId())
                .idClient(domain.getIdClient())
                .idRoom(domain.getIdRoom())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .totalPrice(domain.getTotalPrice())
                .state(domain.getState())
                .build();
    }

    private Reserve toDomain(ReserveEntity entity) {
        return Reserve.builder()
                .id(entity.getId())
                .idClient(entity.getIdClient())
                .idRoom(entity.getIdRoom())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .totalPrice(entity.getTotalPrice())
                .state(entity.getState())
                .build();
    }

=======
        return Mono.empty();
    }
>>>>>>> 6b50b80c95c19ffc3dd2508aca6638645bf2d412
}
