package com.gangE.DongoShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class PostDao {

    private String title;
    private String content;
    private byte[] img;
}
