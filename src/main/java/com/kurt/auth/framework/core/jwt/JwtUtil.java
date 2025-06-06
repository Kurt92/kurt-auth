package com.kurt.auth.framework.core.jwt;

import com.kurt.auth.biz.entity.UserMng;
import com.kurt.auth.framework.core.cookie.JwtTokenEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    //: todo 시크릿키 방식에서 비대칭키 방식으로 바꿀것


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Value("${spring.data.jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateAccessToken(UserMng userMng) {
        return doGenerateToken(userMng, JwtTokenEnum.acc.getExpiredTime());
    }

    public String reGenerateAccessToken(String refreshToken) {

        if(validateToken(refreshToken)) {
            Claims claims = Jwts.claims();
            claims.put("userId", extractAllClaims(refreshToken).get("userId", Long.class));
            claims.put("accountId", extractAllClaims(refreshToken).get("accountId", String.class));
            claims.put("userNm", extractAllClaims(refreshToken).get("userNm", String.class));
            claims.put("email", extractAllClaims(refreshToken).get("email", String.class));

            String jwt = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JwtTokenEnum.acc.getExpiredTime()))
                    .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                    .compact();

            return jwt;
        } else {
            return null;
        }
    }

    public String generateRefreshToken(UserMng userMng) {

        String refreshToken = doGenerateToken(userMng, JwtTokenEnum.ref.getExpiredTime());

        // Redis에 리프레시 토큰 저장 (Key: 사용자 ID, Value: 리프레시 토큰)
        redisTemplate.opsForValue().set(
                "refreshToken : " + userMng.getAccountId(),
                refreshToken,
                JwtTokenEnum.ref.getExpiredTime(),
                TimeUnit.MILLISECONDS
                //Duration.ofDays(7) <- 이렇게 쓰는방법도 있다함. 이게 권장방식이라함
        );

        return refreshToken;
    }

    public String doGenerateToken(UserMng userMng, long expireTime) {

        Claims claims = Jwts.claims();
        claims.put("userId", userMng.getUserId());
        claims.put("accountId", userMng.getAccountId());
        claims.put("userNm", userMng.getUserNm());
        claims.put("email", userMng.getEmail());

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    public Boolean validateToken(String token) {

        String accountId = getUsername(token);
        String storedToken = (String) redisTemplate.opsForValue().get("refreshToken : " + accountId);

        return (storedToken.equals(token) && !isTokenExpired(token));
    }

    public String getUsername(String token) {
        return extractAllClaims(token).get("accountId", String.class);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }




}