package com.gangE.DongoShop.service;

import com.gangE.DongoShop.dto.PostIdProductIdDto;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.model.PostProduct;
import com.gangE.DongoShop.model.Product;
import com.gangE.DongoShop.repository.PostProductRepository;
import com.gangE.DongoShop.repository.PostRepository;
import com.gangE.DongoShop.repository.ProductRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;


@Service
public class PostProductService {

    private final PostProductRepository postProductRepository;
    private final ProductRepository productRepository;
    private final PostRepository postRepository;

    public PostProductService(PostProductRepository postProductRepository, ProductRepository productRepository, PostRepository postRepository) {
        this.postProductRepository = postProductRepository;
        this.productRepository = productRepository;
        this.postRepository = postRepository;
    }

    public PostProduct connectPostProduct(PostIdProductIdDto ids) {
        // 현재 인증된 사용자의 이름 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // PostProduct 객체 생성
        PostProduct postProduct = new PostProduct();


        // 포스트 ID를 기반으로 포스트를 찾습니다.
        Post post = postRepository.findById((long) ids.getPostId())
                .orElseThrow(() -> new NotFoundException("포스트가 없습니다."));

        // 내포스트가아니면 롤백
        // 현재 인증된 사용자의 포스트인지 확인
        if (!post.getPostCustomer().equals(username)) {
            throw new IllegalArgumentException("내 포스트가 아닙니다.");
        }



        // 프로덕트 ID를 기반으로 프로덕트를 찾습니다.
        Product product = productRepository.findById((long) ids.getProductId())
                .orElseThrow(() -> new NotFoundException("상품이 없습니다."));


        // 연결된 프로덕트와 포스트를 설정합니다.
        postProduct.setProduct(product);
        postProduct.setPost(post);

        // 저장 후 반환
        return postProductRepository.save(postProduct);
    }
}
