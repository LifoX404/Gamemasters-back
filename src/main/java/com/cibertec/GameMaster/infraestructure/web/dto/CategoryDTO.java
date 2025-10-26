package com.cibertec.GameMaster.infraestructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;
}
