package com.hotels.reserve_service.domain.port.out;

import com.hotels.reserve_service.domain.model.Reserve;
import reactor.core.publisher.Mono;

public interface IReserveRepositoryPort {

    Mono<Reserve> save(Reserve reserve);
    Mono<Reserve> searchForId(String id);

}
