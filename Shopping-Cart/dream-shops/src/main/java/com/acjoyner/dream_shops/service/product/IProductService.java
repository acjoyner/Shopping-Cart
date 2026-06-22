package com.acjoyner.dream_shops.service.product;

import com.acjoyner.dream_shops.model.Product;
import com.acjoyner.dream_shops.request.AddProductRequest;
import com.acjoyner.dream_shops.request.ProductUpdateRequest;

import java.lang.classfile.instruction.LineNumber;
import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category, String name);
    Long countProductsByBrandAndName(String brand, String name);

}
