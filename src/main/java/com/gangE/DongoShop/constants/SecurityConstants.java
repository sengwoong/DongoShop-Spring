package com.gangE.DongoShop.constants;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public interface SecurityConstants {

    public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    public static final String JWT_HEADER = "Authorization";
    public static final String X_Key = "X-Key";
    public static final String X_Refresh_Token = "X-Refresh-Token";
    public static final byte[] jwtKeyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();



}
