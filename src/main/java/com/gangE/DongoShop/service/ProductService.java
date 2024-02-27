package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.CommentPost;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.model.Product;
import com.gangE.DongoShop.repository.CommentPostRepository;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.PostRepository;
import com.gangE.DongoShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(CommentPostRepository commentRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }



    private Customer getCurrentCustomer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerRepository.findByName(username);
    }


    // 전체 프로덕트 가져오기
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 프로덕트 검색 (프로덕트 아이디)
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long productId) {


        return productRepository.findById(productId);
    }



    @Transactional
    public Product addNewProduct(Product product) {
        Customer customer = getCurrentCustomer();
        product.setProductCustomer(customer); // 현재 고객을 제품에 할당
        return productRepository.save(product); // 제품 저장 후 반환
    }



    // 프로덕트 삭제
    @Transactional
    public void deleteProduct(Long productId) {
        // 현재 고객 가져오기
        Customer customer = getCurrentCustomer();

        // 주어진 productId로 제품 가져오기
        Optional<Product> productOptional = productRepository.findById(productId);

        // 제품이 존재하지 않는 경우 또는 해당 제품이 현재 고객이 만든 제품이 아닌 경우
        if (productOptional.isEmpty() || !(productOptional.get().getProductCustomer() == customer)) {
            // 삭제할 수 없음
            return;
        }

        // 제품 삭제
        productRepository.delete(productOptional.get());
    }


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

    @Transactional
    public void updateProduct(Long productId, Product updatedProduct) {
        // 현재 고객 가져오기
        Customer customer = getCurrentCustomer();

        // 주어진 productId로 제품 가져오기
        Optional<Product> productOptional = productRepository.findById(productId);

        // 제품이 존재하지 않는 경우 또는 해당 제품이 현재 고객이 만든 제품이 아닌 경우
        if (productOptional.isEmpty() || !(productOptional.get().getProductCustomer() == customer)) {
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
        if (updatedProduct.getType() != null) {
            productToUpdate.setType(updatedProduct.getType());
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








    // todo 다음으로 할것

    // 단어장을 상품에 연결하기

    // 문제장을 상품에 연결하기



    // todo 노트와 프로덕트가 연결되었을때  프로덕트의 다운로드수를 장가시킴
}
