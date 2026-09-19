package com.hotels.reserve_service.infraestructure.adapter.in.dto;

import java.time.LocalDateTime;

public record ReserveRequestDTO (String idClient, String idRoom, LocalDateTime startDate,
                                 LocalDateTime endDate, Double totalPrice){}
