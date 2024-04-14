package com.gangE.DongoShop.util;

import com.gangE.DongoShop.model.Customer;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateAt {

    public static String CreateAt() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 날짜와 시간을 원하는 형식으로 포맷팅합니다. 예: "yyyy-MM-dd HH:mm:ss"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }




}
