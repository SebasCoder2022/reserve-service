package com.hotels.reserve_service.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
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
