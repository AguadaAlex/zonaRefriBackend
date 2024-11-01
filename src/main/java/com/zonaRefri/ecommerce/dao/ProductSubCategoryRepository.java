package com.zonaRefri.ecommerce.dao;
import com.zonaRefri.ecommerce.entity.Product;
import com.zonaRefri.ecommerce.entity.ProductoSubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "productSubCategory", path = "product-subcategory")
public interface ProductSubCategoryRepository extends JpaRepository<ProductoSubCategory, Long> {
    Page<ProductoSubCategory> findByCategoryId(@Param("id") Long id , Pageable pageable);
}
