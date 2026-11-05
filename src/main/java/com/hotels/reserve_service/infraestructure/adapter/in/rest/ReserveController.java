package com.hotels.reserve_service.infraestructure.adapter.in.rest;


import com.hotels.reserve_service.domain.model.Reserve;
import com.hotels.reserve_service.domain.port.in.ICancelReserveUseCase;
import com.hotels.reserve_service.domain.port.in.IConfirmReserveUseCase;
import com.hotels.reserve_service.domain.port.in.ICreateReserveUseCase;
import com.hotels.reserve_service.infraestructure.adapter.in.dto.CancelReserveDTO;
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
    private final IConfirmReserveUseCase confirmReserveUseCase;
    private final ICancelReserveUseCase cancelReserveUseCase;

    @PostMapping
    public Mono<ResponseEntity<Reserve>> createReserve(@RequestBody ReserveRequestDTO dto){

        Reserve reserve = Reserve.builder()
                .idClient(dto.idClient())
                .idRoom(dto.idRoom())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .totalPrice(dto.totalPrice())
                .build(); // Toma CreatedState() por defecto automáticamente

        return createReserveUseCase.create(reserve)
                .map(savedReserve -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(savedReserve)
                );
    }

    @PatchMapping("/{id}/confirm")

    public Mono<ResponseEntity<Reserve>> confirmReserve(@PathVariable String id){
        return confirmReserveUseCase.confirm(id)
                .map(ResponseEntity::ok);
    }

    @PatchMapping("/{id}/cancel")
    public Mono<ResponseEntity<Reserve>> cancelReserve(
            @PathVariable String id,
            @RequestBody(required = false) CancelReserveDTO dto){
        String reason = dto != null && dto.reason() != null ? dto.reason() : "Cancelada por el cliente";
        return cancelReserveUseCase.cancel(id, reason)
                .map(ResponseEntity::ok);
    }

}
