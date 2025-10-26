package com.cibertec.GameMaster.infraestructure.web.controllers;

import com.cibertec.GameMaster.application.services.CategoryService;
import com.cibertec.GameMaster.infraestructure.web.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/get/{id}")
    public CategoryDTO getCategory(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @GetMapping("/get")
    public List<CategoryDTO> getCategories() {
        return service.getCategories();
    }
}
