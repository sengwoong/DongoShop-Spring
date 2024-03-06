package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.model.Product;
import com.gangE.DongoShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("select/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // 제품 ID를 사용하여 특정 제품을 반환하는 엔드포인트
    @GetMapping("select/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Optional<Product> productOptional = productService.getProductById(productId);
        return productOptional
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // 새로운 제품 추가 엔드포인트
    @PostMapping("/create")
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product) {
        Product newProduct = productService.addNewProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    // 제품 업데이트 엔드포인트
    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        productService.updateProduct(productId, updatedProduct);
        return ResponseEntity.ok("Product updated successfully");
    }

    // 제품 삭제 엔드포인트
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    // 기타 엔드포인트 추가 가능
}
