package com.acjoyner.dream_shops.service.product;


import com.acjoyner.dream_shops.dto.ImageDto;
import com.acjoyner.dream_shops.dto.ProductDto;
import com.acjoyner.dream_shops.exceptions.ResourceNotFoundException;
import com.acjoyner.dream_shops.model.Category;
import com.acjoyner.dream_shops.model.Image;
import com.acjoyner.dream_shops.model.Product;
import com.acjoyner.dream_shops.repository.ImageRepository;
import com.acjoyner.dream_shops.repository.ProductRepository;
import com.acjoyner.dream_shops.repository.CategoryRepository;
import com.acjoyner.dream_shops.request.AddProductRequest;
import com.acjoyner.dream_shops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in the DB
        // If yes, set it as the new product category
        // If not, save it as a new category then set it as the new product category.
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory());
                    return categoryRepository.save(newCategory);
                });
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category

        );
    }



    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                ()-> {throw new ResourceNotFoundException("Product not found!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory());
        existingProduct.setCategory(category);
        return existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrandContainingIgnoreCase(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
