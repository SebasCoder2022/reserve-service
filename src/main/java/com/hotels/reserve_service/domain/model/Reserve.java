package com.hotels.reserve_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Reserve {

    private String id;
    private String idClient;
    private String idRoom;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double totalPrice;
    private String state;

}
