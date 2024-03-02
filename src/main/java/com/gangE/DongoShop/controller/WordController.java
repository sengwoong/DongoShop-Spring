package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.QDto.ProductIdAndWordDto;
import com.gangE.DongoShop.dto.WordDto;
import com.gangE.DongoShop.model.Word;
import com.gangE.DongoShop.model.WordProduct;
import com.gangE.DongoShop.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    @PostMapping("/create/{productId}")
    public Word addWord( @PathVariable long productId , @RequestBody WordDto word) {
        return wordService.saveWord(word.getWord(), word.getDefinition(),productId);
    }

    @GetMapping("select/{WordId}")
    public Word getWordById(@PathVariable long WordId) {
        return wordService.getWordById(WordId);
    }


    // {id} 는 모두 프로덕트아이디
    @GetMapping("select_all/{productId}")
    public List<ProductIdAndWordDto> SelectAllWordById(@PathVariable Long productId) {
        return wordService.selectAllWord(productId);
    }


// todo  단어를 삭제 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 식제
    @DeleteMapping("delect/product/{productId}/word/{wordId}")
    public WordProduct DelectWordById(@PathVariable Long productId, @PathVariable Long wordId) {
        return wordService.DelectMyWord(productId,wordId);
    }
    // todo  단어를 업데이트 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 업데이트

    @PostMapping("update/product/{productId}/word/{wordId}")
    public Optional<Word> UpdateWordById(@PathVariable Long productId, @PathVariable Long wordId, @RequestBody WordDto word) {
        return wordService.UpdateMyWord(productId,wordId,word);
    }



}
