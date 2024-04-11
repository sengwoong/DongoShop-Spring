package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Product;
import com.gangE.DongoShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CustomerService customerService;
    @Autowired
    public ProductService(CustomerService customerService, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.customerService = customerService;
    }

    // 조건부검색
    public Page<Product> searchProducts(String type, String content, String downCountOrder, String currentOrder, int page, int size) {
        return productRepository.searchInAllProducts(type, content, downCountOrder, currentOrder, page, size);

    }


    @Transactional(readOnly = true)
    public Page<Product> GetAllMyProduct(Pageable pageable) {
        // 현재 로그인한 사용자 정보 가져오기
        Customer customer = customerService.getCurrentCustomer();
        return productRepository.findByUser(customer, pageable);
    }


    // 문제 및 단어 순으로 쿼리 DSL로 리펙토링
    @Transactional(readOnly = true)
    public Page<Product> GetVisibleProduct(Pageable pageable) {
        // visible 필드가 true인 프로덕트만 가져오기
        return productRepository.findByVisible(true, pageable);
    }

    // 프로덕트 검색 (프로덕트 아이디)
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }
    @Transactional
    public Product addNewProduct(Product product) {
        try {
            Customer customer = customerService.getCurrentCustomer();
            product.setVisible(false);

            // 유저아이디에 값넣기 변경
            product.setUser(customer); // 현재 고객을 제품에 할당
            product.setDownloadCount(0);
            System.out.println("product");
            System.out.println(product);
            return productRepository.save(product); // 제품 저장 후 반환
        } catch (DataAccessException e) {
            // 데이터 접근 예외 처리
            e.printStackTrace(); // 로깅
            // 적절한 응답 반환\
            System.out.printf(String.valueOf(e));

        } catch (Exception e) {
            // 그 외 예외 처리
            e.printStackTrace(); // 로깅
            // 적절한 응답 반환
            System.out.printf(String.valueOf(e));
        }
        return productRepository.save(product);
    }

    // 프로덕트 삭제
    @Transactional
    public void deleteProduct(Long productId) {
        // 현재 고객 가져오기
        Customer customer =  customerService.getCurrentCustomer();
        // 주어진 productId로 제품 가져오기
        Optional<Product> productOptional = productRepository.findById(productId);
        // 제품이 존재하지 않는 경우 또는 해당 제품이 현재 고객이 만든 제품이 아닌 경우
        if (productOptional.isEmpty() || !(productOptional.get().getUser() == customer)) {
            // 삭제할 수 없음
            return;
        }
        // 제품 삭제
        try {
            productRepository.delete(productOptional.get());
        } catch (Exception e) {
            System.out.println(e);
        }

    }

// 강의 내용으로 사용하여 주석으로 남겨둡니다 아래와 비교하여 사용할수 있습니다.
//    @Transactional
//    public void updateProduct(Long productId, Product updatedProduct) {
//        // 현재 고객 가져오기
//        Customer customer = getCurrentCustomer();
//
//        // 주어진 productId로 제품 가져오기
//        Optional<Product> productOptional = productRepository.findById(productId);
//
//        // 제품이 존재하지 않는 경우 또는 해당 제품이 현재 고객이 만든 제품이 아닌 경우
//        if (productOptional.isEmpty() || !(productOptional.get().getProductCustomer() == customer)) {
//            // 업데이트할 수 없음
//            return;
//        }
//
//        // 업데이트할 제품 가져오기
//        Product productToUpdate = productOptional.get();
//
//        // 새로운 정보로 제품 업데이트
//        productToUpdate.setTitle(updatedProduct.getTitle());
//        productToUpdate.setContent(updatedProduct.getContent());
//        productToUpdate.setType(updatedProduct.getType());
//        productToUpdate.setDownloadCount(updatedProduct.getDownloadCount());
//        productToUpdate.setPrice(updatedProduct.getPrice());
//
//        // 업데이트된 제품 저장
//        productRepository.save(productToUpdate);
//    }


    // 다이나믹 업데이트 이전 로직
    @Transactional
    public void updateProduct(Long productId, Product updatedProduct) {
        // 현재 고객 가져오기
        Customer customer =  customerService.getCurrentCustomer();
        // 주어진 productId로 제품 가져오기
        Optional<Product> productOptional = productRepository.findById(productId);
        // 제품이 존재하지 않는 경우 또는 해당 제품이 현재 고객이 만든 제품이 아닌 경우
        if (productOptional.isEmpty() || !(productOptional.get().getUser() == customer)) {
            // 업데이트할 수 없음
            return;
        }
        // 업데이트할 제품 가져오기
        Product productToUpdate = productOptional.get();
        // 업데이트할 필드만 변경
        if (updatedProduct.getTitle() != null) {
            productToUpdate.setTitle(updatedProduct.getTitle());
        }
        if (updatedProduct.getContent() != null) {
            productToUpdate.setContent(updatedProduct.getContent());
        }

        if (updatedProduct.getDownloadCount() != 0) {
            productToUpdate.setDownloadCount(updatedProduct.getDownloadCount());
        }
        if (updatedProduct.getPrice() != 0) {
            productToUpdate.setPrice(updatedProduct.getPrice());
        }
        // 업데이트된 제품 저장
        productRepository.save(productToUpdate);
    }



}
