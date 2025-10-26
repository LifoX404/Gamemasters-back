package com.cibertec.GameMaster.infraestructure.web.dto;

import java.time.LocalDateTime;

public record UpdateCustomerRequest(String firstName,
                                    String lastName,
                                    String email,
                                    String phone,
                                    String address,
                                    LocalDateTime createdAt
) {
}
