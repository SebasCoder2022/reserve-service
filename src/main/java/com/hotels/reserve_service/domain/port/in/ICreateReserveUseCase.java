package com.hotels.reserve_service.domain.port.in;

import com.hotels.reserve_service.domain.model.Reserve;
import reactor.core.publisher.Mono;

public interface ICreateReserveUseCase {

    Mono<Reserve> create(Reserve reserve);

}
