package com.gangE.DongoShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@AllArgsConstructor
public class WordDto {
    private String word;
    private String definition;

}
