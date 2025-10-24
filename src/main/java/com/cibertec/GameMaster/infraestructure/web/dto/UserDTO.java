package com.cibertec.GameMaster.infraestructure.web.dto;

import com.cibertec.GameMaster.infraestructure.database.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String email;

    private String password;

    private RoleType role;

    private LocalDateTime createdAt;

    private String imgProfile;

    private boolean status;
}
