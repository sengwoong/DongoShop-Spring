package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.QDto.ProductIdAndWordDto;
import com.gangE.DongoShop.dto.WordDto;
import com.gangE.DongoShop.model.Word;
import com.gangE.DongoShop.model.WordProduct;
import com.gangE.DongoShop.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Tag(name = "Word Controller", description = "단어")
@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;
    @Operation(summary = "select_all", description = "해당 프로덕트의 모든 단어를 검색합니다.")
    @GetMapping("select_all/{productId}")
    public List<ProductIdAndWordDto> SelectAllWordById(@PathVariable Long productId) {
        return wordService.selectAllWord(productId);
    }
    @Operation(summary = "create", description = "해당 프로덕트를 파람으로 받으며 단어를 생성 합니다.")
    @PostMapping("/create/{productId}")
    public Word addWord( @PathVariable long productId , @RequestBody WordDto word) {
        return wordService.saveWord(word.getWord(), word.getDefinition(),productId);
    }
    @Operation(summary = "delect", description = "해당 프로덕트와 단어를 파람으로 받으며 단어를 삭제 합니다.")
    @DeleteMapping("delect/product/{productId}/word/{wordId}")
    public WordProduct DelectWordById(@PathVariable Long productId, @PathVariable Long wordId) {
        return wordService.DelectMyWord(productId,wordId);
    }

    @Operation(summary = "update", description = "해당 프로덕트와 단어를 파람으로 받으며 단어를 수정 합니다.")
    @PostMapping("update/product/{productId}/word/{wordId}")
    public Optional<Word> UpdateWordById(@PathVariable Long productId, @PathVariable Long wordId, @RequestBody WordDto word) {
        return wordService.UpdateMyWord(productId,wordId,word);
    }



}
