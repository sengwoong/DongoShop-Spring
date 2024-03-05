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



    // {id} 는 모두 프로덕트아이디
    @GetMapping("select_all/{productId}")
    public List<ProductIdAndWordDto> SelectAllWordById(@PathVariable Long productId) {
        return wordService.selectAllWord(productId);
    }


    @DeleteMapping("delect/product/{productId}/word/{wordId}")
    public WordProduct DelectWordById(@PathVariable Long productId, @PathVariable Long wordId) {
        return wordService.DelectMyWord(productId,wordId);
    }


    @PostMapping("update/product/{productId}/word/{wordId}")
    public Optional<Word> UpdateWordById(@PathVariable Long productId, @PathVariable Long wordId, @RequestBody WordDto word) {
        return wordService.UpdateMyWord(productId,wordId,word);
    }



}
