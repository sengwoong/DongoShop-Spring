package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.WordDto;
import com.gangE.DongoShop.model.Word;
import com.gangE.DongoShop.service.LikePostService;
import com.gangE.DongoShop.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/word")
public class WordController {


    @Autowired
    private WordService wordService;

    @PostMapping("/create")
    public Word addWord(@RequestBody WordDto word) {
        return wordService.saveWord(word.getWord(), word.getDefinition());
    }

    @GetMapping("select/{id}")
    public Word getWordById(@PathVariable long id) {
        return wordService.getWordById(id);
    }



}
