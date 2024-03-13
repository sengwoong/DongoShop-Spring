package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.model.Product;
import com.gangE.DongoShop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Tag(name = "Product Controller", description = "제품")
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 페이지 네이션 적용
    @Operation(summary = "select_all paging", description = "나의 모든 제품을 들고 옵나다")
    @GetMapping("select_my_all")
    public ResponseEntity<Page<Product>> GetAllMyProduct(Pageable pageable) {
        Page<Product> products = productService.GetAllMyProduct(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "select_visible_all paging", description = "나의 모든 제품을 들고 옵나다")
    @GetMapping("select_visible_all")
    public ResponseEntity<Page<Product>> GetVisibleMyProduct(Pageable pageable) {
        Page<Product> products = productService.GetVisibleProduct(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @Operation(summary = "select", description = "특정 제품 하나를 가져옵니다.")
    @GetMapping("select/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Optional<Product> productOptional = productService.getProductById(productId);
        return productOptional
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // 새로운 제품 추가 엔드포인트
    @Operation(summary = "create", description = "제품을 만듭니다.")
    @PostMapping("/create")
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product) {
        Product newProduct = productService.addNewProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    // 제품 업데이트 엔드포인트
    @Operation(summary = "update", description = "프로덕트 아이디를 받으면 자신이만든 제품을 업데이트 합니다.")
    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        productService.updateProduct(productId, updatedProduct);
        return ResponseEntity.ok("Product updated successfully");
    }

    // 제품 삭제 엔드포인트
    @Operation(summary = "delete", description = "프로덕트 아이디를 받으면 자신이만든 제품을 삭제 합니다.")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    // 기타 엔드포인트 추가 가능
}
