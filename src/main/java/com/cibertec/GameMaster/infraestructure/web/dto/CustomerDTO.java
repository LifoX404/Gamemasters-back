package com.cibertec.GameMaster.infraestructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private LocalDateTime createdAt;
}
