package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Word;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.WordRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordService {

    private final WordRepository wordRepository;


    @Autowired
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;

    }




    public Word saveWord(String word, String definition) {

        if ( definition == null || word == null) {
            throw new IllegalArgumentException("단어와 정의를 모두 제공해야합니다.");
        }
        Word newWord = new Word();
        newWord.setWord(word);
        newWord.setDefinition(definition);
        return wordRepository.save(newWord);
    }


    public Word getWordById(long id) {

        Optional<Word> optionalWord = wordRepository.findById(id);
        return optionalWord.orElse(null);
    }






}
