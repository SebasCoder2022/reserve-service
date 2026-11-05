package com.hotels.reserve_service.domain.port.in;

import com.hotels.reserve_service.domain.model.Reserve;
import reactor.core.publisher.Mono;

public interface ICancelReserveUseCase {
    Mono<Reserve> cancel(String id, String reason);
}
