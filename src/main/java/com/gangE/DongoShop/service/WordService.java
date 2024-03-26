package com.gangE.DongoShop.service;




import com.gangE.DongoShop.dto.PrevIdCurrnetId;
import com.gangE.DongoShop.dto.QDto.ProductIdAndWordDto;
import com.gangE.DongoShop.dto.WordDto;
import com.gangE.DongoShop.model.*;
import com.gangE.DongoShop.repository.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;



@Service
public class WordService   {

    private final WordRepository wordRepository;
    private final ProductRepository productRepository;
    private final WordProductRepository wordProductRepository;
    private final CustomerService customerService;
    @Autowired
    public WordService(WordRepository wordRepository, WordProductRepository wordProductRepository,
                       ProductRepository productRepository, CustomerRepository customerRepository ,  CustomerService customerService
                        ) {
        this.wordRepository = wordRepository;
        this.wordProductRepository = wordProductRepository;
        this.productRepository = productRepository;
        this.customerService = customerService;
    }

    @Autowired
    private JPAQueryFactory queryFactory;



    private Product IsMyProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        if (product.getUser() !=  customerService.getCurrentCustomer()) {
            throw new IllegalArgumentException("나의 포스트가 아닙니다.");
        }
        return product;
    }


    public Word saveWord(String wordText, String definition, Long productId) {

        if (definition == null || wordText == null || productId == null) {
            throw new IllegalArgumentException("단어, 정의 및 제품 ID를 모두 제공해야합니다.");
        }

        Word newWord = new Word();
        newWord.setWord(wordText);
        newWord.setDefinition(definition);
        wordRepository.save(newWord); // Save the new word

        // Retrieve the product
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        System.out.println(findMaxWordLocalForProduct(product));
        // 월드 프로덕트애 등록
        WordProduct wordProduct = new WordProduct();
        wordProduct.setProduct(product);
        wordProduct.setWord(newWord); // 새단어 하나만 등
        wordProduct.setWordLocal(findMaxWordLocalForProduct(product));// 해당 프로덕트의 setWordLocal 중 가장 큰값으로 반환
        wordProductRepository.save(wordProduct); // 저장

        return newWord;
    }

    private int findMaxWordLocalForProduct(Product product) {
        Integer maxWordLocal = wordProductRepository.findMaxWordLocalByProduct((long) product.getId());
        return maxWordLocal == null ?  1: maxWordLocal + 1 ;
    }





    // todo  단어를 업데이트 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 업데이트

    public WordProduct DelectMyWord(Long productId, Long wordId ) {

        Product product =  IsMyProduct(productId);
        if(product == null){
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }
        // 전체 에서 해당 wordId 가 있는지 검색
        WordProduct MyWord= selectMyWordProduct(productId,wordId);
        // 해당 단어를삭제

        wordProductRepository.delete(MyWord);

        return MyWord;
    }



    public Optional<Word> UpdateMyWord(Long productId, Long wordId ,WordDto word) {
        // 전체 에서 해당 wordId 가 있는지 검색
        Optional<Word> MyWord= selectWord(productId,wordId);
        // 해트단어를 업데이트

        Product product =  IsMyProduct(productId);
        if(product == null){
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }


        MyWord.get().setWord(word.getWord());
        MyWord.get().setDefinition(word.getDefinition());

        // 엔티티가 자동업데이
        return MyWord;
    }




    @Transactional
    public void exchangeWordByLocalId(int productId, PrevIdCurrnetId wordLocal) {
        // 전체에서 해당 wordId가 있는지 검색
        WordProduct prevWord = wordProductRepository.findByProductIdAndWordLocal(productId, wordLocal.getPrevId());
        WordProduct currentWord = wordProductRepository.findByProductIdAndWordLocal(productId, wordLocal.getCurrentId());

        Product product =  IsMyProduct((long)productId);
        if(product == null){
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }


        // 검색된 WordProduct가 null이 아닌 경우에만 로직을 진행
        if (prevWord != null && currentWord != null) {
            // prevWord와 currentWord를 서로 교환
            String temp = String.valueOf(prevWord.getWordLocal());
            prevWord.setWordLocal(currentWord.getWordLocal());
            currentWord.setWordLocal(Integer.parseInt(temp));
        } else {
            // 예외 처리 또는 로그 등의 추가 작업을 수행할 수 있음
            // 예를 들어, WordProduct가 찾아지지 않을 때 로그를 출력하거나 예외를 던질 수 있음
            throw new EntityNotFoundException("WordProduct not found for productId: " + productId + " and wordId: " + wordLocal);
        }
    }





    public List<ProductIdAndWordDto> selectAllWord(Long productId ) {

        if ( productId == null) {
            throw new IllegalArgumentException("해당 프로덕트의 모든단어를 검색할려면 프로덕트 아이디가 필요합니다.");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        List<ProductIdAndWordDto> words =  wordRepository.searchInAllWord(product);

        return  words;
    }


    @Transactional
    public WordProduct selectMyWordProduct(Long productId, Long wordId ) {

        if ( productId == null || wordId==null) {
            throw new IllegalArgumentException("해당 프로덕트의 모든단어를 검색할려면 프로덕트 아이디와 단어아이디가 필요합니다.");
        }

        Product product =  IsMyProduct(productId);
        WordProduct wordProducts =  wordRepository.searchInProductWord(product,wordId);

        return  wordProducts;
    }


    @Transactional
    public Optional<Word> selectWord(Long productId, Long wordId ) {

        if ( productId == null || wordId==null) {
            throw new IllegalArgumentException("해당 프로덕트의 모든단어를 검색할려면 프로덕트 아이디와 단어아이디가 필요합니다.");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        if (product.getUser() !=  customerService.getCurrentCustomer()) {
            throw new IllegalArgumentException("나의 포스트가 아닙니다.");
        }

        Optional<Word> word =  wordRepository.findById(wordId);

        if (word.isEmpty()) {
            throw new IllegalArgumentException("단어가 없습니다.");
        }

        return  word;
    }






}




