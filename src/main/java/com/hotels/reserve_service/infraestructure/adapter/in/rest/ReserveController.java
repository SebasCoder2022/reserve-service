package com.hotels.reserve_service.infraestructure.adapter.in.rest;


import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.port.in.ICreateReserveUseCase;
import com.hotels.reserve_service.infraestructure.adapter.in.dto.ReserveRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reserves")
@RequiredArgsConstructor

public class ReserveController {

    private final ICreateReserveUseCase createReserveUseCase;

    @PostMapping
    public Mono<ResponseEntity<Reserve>> createReserve(@RequestBody ReserveRequestDTO dto){

        Reserve reserve = new Reserve(
                null,
                dto.idClient(),
                dto.idRoom(),
                dto.startDate(),
                dto.endDate(),
                dto.totalPrice(),
                "CREATED"
        );

        return createReserveUseCase.create(reserve)
                .map(savedReserve -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(savedReserve)
                );
    }

}
