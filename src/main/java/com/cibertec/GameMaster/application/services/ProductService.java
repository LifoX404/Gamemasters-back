package com.cibertec.GameMaster.application.services;

import com.cibertec.GameMaster.application.port.CategoryPort;
import com.cibertec.GameMaster.application.port.ProductPort;
import com.cibertec.GameMaster.infraestructure.database.entity.Category;
import com.cibertec.GameMaster.infraestructure.database.entity.Product;
import com.cibertec.GameMaster.infraestructure.mapper.ProductMapper;
import com.cibertec.GameMaster.infraestructure.web.dto.product.ProductDto;
import com.cibertec.GameMaster.infraestructure.web.dto.product.UpdateProductRequest;
import com.cibertec.GameMaster.infraestructure.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductPort productPort;

    @Autowired
    private CategoryPort categoryPort;

    @Autowired
    private ProductMapper mapper;


    public ProductDto getProductById(Long id) {
        return mapper.toDTO(productPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id)));
    }

    public List<ProductDto> getProducts() {
        return productPort.getProducts().stream()
                .map(product -> mapper.toDTO(product))
                .toList();
    }

    public ProductDto createProduct(UpdateProductRequest request) {

        Category categoryEntity = categoryPort.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", request.categoryId()));

        Product entity = Product.builder()
                .name(request.name())
                .description(request.description())
                .category(categoryEntity)
                .details(request.details())
                .price(request.price())
                .stock(request.stock())
                .imgUrl(request.imgUrl())
                .build();

        productPort.save(entity);
        return mapper.toDTO(entity);
    }

    public void updateProduct(Long id, UpdateProductRequest request) {
        Product entity = productPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));


        Category category = categoryPort.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", request.categoryId()));


        entity.setName(request.name());
        entity.setDescription(request.description());

        entity.setCategory(category);

        entity.setPrice(request.price());
        entity.setStock(request.stock());
        entity.setImgUrl(request.imgUrl());

        productPort.save(entity);
    }

    public void deleteProduct(Long id){
        productPort.logicDelete(id);
    }
}
