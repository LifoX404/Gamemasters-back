package com.cibertec.GameMaster.infraestructure.web.dto.auth;

import com.cibertec.GameMaster.infraestructure.database.entity.RoleType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "roleType", "customerId", "jwt", })
public record AuthResponse(
        String username,
        RoleType roleType,
        Long customerId,
        String jwt
) {
}
