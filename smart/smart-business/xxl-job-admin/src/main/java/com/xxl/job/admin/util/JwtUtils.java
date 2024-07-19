package com.xxl.job.admin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtUtils {
    // 密钥
    private static final String secret = "111111";


    /**
     * 验证token合法性
     */
    public static void tokenVerify(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        jwtVerifier.verify(token);//没报错说明验证成功
        JWT.decode(token).getExpiresAt();
    }

}
