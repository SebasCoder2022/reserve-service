package com.hotels.reserve_service.infraestructure.adapter.out.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("reserves")
public class ReserveEntity {

    @Id
    private String id;

    @Column("id_client")
    private String idClient;

    @Column("id_room")
    private String idRoom;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("total_price")
    private Double totalPrice;

    private String state;
}
