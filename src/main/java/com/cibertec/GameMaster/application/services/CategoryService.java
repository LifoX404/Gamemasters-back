package com.cibertec.GameMaster.application.services;

import com.cibertec.GameMaster.application.port.CategoryPort;
import com.cibertec.GameMaster.infraestructure.mapper.CategoryMapper;
import com.cibertec.GameMaster.infraestructure.web.dto.CategoryDTO;
import com.cibertec.GameMaster.infraestructure.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper mapper;

    @Autowired
    private CategoryPort port;

    public CategoryDTO getCategoryById(Long id) {
        return mapper.toDTO(port.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id)));
    }

    public List<CategoryDTO> getCategories() {
        return port.findAll().stream()
                .map(product -> mapper.toDTO(product))
                .toList();
    }
}
