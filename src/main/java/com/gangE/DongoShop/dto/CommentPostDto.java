package com.gangE.DongoShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CommentPostDto {
    private String commentText;
    private Long toUser;
}


