package com.cibertec.GameMaster.infraestructure.database.repository;

import com.cibertec.GameMaster.infraestructure.database.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Modifying
//    @Transactional
//    @Query("UPDATE Category c SET c.status = false WHERE c.id = :id")
//    boolean setStatusFalse(@Param("id") UUID id);



}
