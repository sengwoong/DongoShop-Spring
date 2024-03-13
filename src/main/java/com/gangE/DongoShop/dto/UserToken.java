package com.gangE.DongoShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserToken {
    private Long userId;
    private String token;

}
