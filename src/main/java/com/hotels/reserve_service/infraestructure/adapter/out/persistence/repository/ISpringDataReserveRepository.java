package com.hotels.reserve_service.infraestructure.adapter.out.persistence.repository;

import com.hotels.reserve_service.infraestructure.adapter.out.persistence.entity.ReserveEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ISpringDataReserveRepository extends ReactiveCrudRepository<ReserveEntity, String> {
}
