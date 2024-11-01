package com.zonaRefri.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="product_subcategory")
@Getter
@Setter
public class ProductoSubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "subcategory_name")
    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subCategory")
    private Set<Product> products;

}
