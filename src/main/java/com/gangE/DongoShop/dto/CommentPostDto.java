package com.gangE.DongoShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public class CommentPostDto {
    private String commentText;
    private Long toUser;
}


