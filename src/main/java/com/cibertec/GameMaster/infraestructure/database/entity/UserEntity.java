package com.cibertec.GameMaster.infraestructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private String imgProfile;
    // "https://i.pinimg.com/1200x/15/0f/a8/150fa8800b0a0d5633abc1d1c4db3d87.jpg";

    @Builder.Default
    @Column(columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status = true;


}