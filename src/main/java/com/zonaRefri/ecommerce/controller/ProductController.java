package com.zonaRefri.ecommerce.controller;
import com.zonaRefri.ecommerce.entity.Product;
import com.zonaRefri.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PagedResourcesAssembler;

@RestController
public class ProductController {
    @Autowired private ProductService productService;
    @Autowired private PagedResourcesAssembler<Product> pagedResourcesAssembler;
    @GetMapping("api/products/search/buscarPorCategoria")
    @CrossOrigin("http://localhost:4200")
    public PagedModel<EntityModel<Product>> getProductsByCategoryId(@RequestParam(name = "id") Long categoryId, Pageable pageable)
    { Page<Product> productsPage = productService.getProductsByCategoryId(categoryId, pageable);
        return pagedResourcesAssembler.toModel(productsPage, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class) .getProductsByCategoryId(categoryId, pageable)).withSelfRel()); } }