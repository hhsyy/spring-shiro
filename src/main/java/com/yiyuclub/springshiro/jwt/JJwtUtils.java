package com.yiyuclub.springshiro.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * jjwt maven包
 */
public class JJwtUtils {
    //过期时间
    private static final long DATE_EXPIRE = 30 * 60 * 1000;
    //私钥
    private static final String TOKEN_SECRET = "yiyu_club";


    /*
   iss：发行人
   exp：到期时间
   sub：主题
   aud：用户
   nbf：在此之前不可用
   iat：发布时间
   jti：JWT ID用于标识该JWT
    */
    public static String getToken(String name, String userInfo) {
        Date date = new Date(System.currentTimeMillis() + DATE_EXPIRE);

        JwtBuilder builder = Jwts.builder()
                .setAudience(name)
                .setId(userInfo)
                .setExpiration(date)
                .setIssuer("yiyu")
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET);

        return builder.compact();
    }

    public static boolean checkToken(String token) {

        try {
            Claims claims = Jwts.parser()
                    .requireIssuer("yiyu")
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static Claims getTokenDetails(String token) {

        Claims claims = Jwts.parser()
                .requireIssuer("yiyu")
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }
}
