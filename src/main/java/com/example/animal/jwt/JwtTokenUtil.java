package com.example.animal.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.animal.entity.User;

import com.auth0.jwt.JWT;

import java.security.Key;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    // Claim 中的用户名
    private static final String CLAM_KEY_USERNAME = "sub";

    // Claim 中的创建时间
    private static final String CLAM_KEY_CREATED = "created";

    private final Key secretKey;

    // JWT 密钥
    @Value("${jwt.secret}")
    private String secret;

    // JWT 过期时间
    @Value("${jwt.expiration}")
    private Long expiration;

    // Authorization 请求头中的 token 字符串的开头部分（Bearer）
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    public JwtTokenUtil() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 根据负载生成 JWT 的 token
     * @param user 负载
     * @return JWT 的 token
     */
    private String generateToken(User user){
        return JWT.create()
                .withIssuer("auth0")
                .withIssuedAt(new Date()) // 设置创建时间
                .withClaim("userId", user.getId())
                .withExpiresAt(generateExpirationDate())
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 生成 token 的过期时间
     * @return token 的过期时间
     */
    private Date generateExpirationDate(){
        /*
            Date 构造器接受格林威治时间，推荐使用 System.currentTimeMillis() 获取当前时间距离 1970-01-01 00:00:00 的毫秒数
            而我们在配置文件中配置的是秒数，所以需要乘以 1000。
            一般而言 Token 的过期时间为 7 天，因此我们一般在 Spring Boot 的配置文件中将 jwt.expiration 设置为 604800，
            即 7 * 24 * 60 * 60 = 604800 秒。
         */
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从 token 中获取 JWT 中的负载
     * @param token JWT 的 token
     * @return JWT 中的负载
     */
    public DecodedJWT verify(String token){
        DecodedJWT jwt = null;
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)) // 创建一个 JWT 验证器
                    .withIssuer("auth0")// 设置发行者（issuer）条件
                    .build();// 构建验证器
             jwt = verifier.verify(token); // 验证 JWT
        }catch (Exception e){
            LOGGER.info("JWT 格式验证失败：{}",token);
        }
        return jwt;
    }

    /**
     * 验证 token 是否过期
     * @param token JWT 的 token
     * @return token 是否过期 true：过期 false：未过期
     */
    private boolean isTokenExpired(String token){
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从 token 中获取过期时间
     * @param token JWT 的 token
     * @return 过期时间
     */
    private Date getExpiredDateFromToken(String token){
        return verify(token).getExpiresAt();
    }

    /**
     * 判断 token 是否可以被刷新
     * @param token JWT 的 token
     * @param time 指定时间段（单位：秒）
     * @return token 是否可以被刷新 true：可以 false：不可以
     */
    private boolean tokenRefreshJustBefore(String token,int time){
        // 解析 JWT 的 token 拿到负载
        DecodedJWT decodedJWTFromToken = verify(token);
        if (decodedJWTFromToken == null) {
            return false; // 如果无法解析 token，返回 false
        }
        // 获取 token 的创建时间
        Date tokenCreateDate =decodedJWTFromToken.getIssuedAt();
        if (tokenCreateDate == null) {
            return false; // 如果没有创建时间，返回 false
        }
        // 获取当前时间
        Date refreshDate = new Date();
        // 指定时间段后的时间
        Date refreshExpirationDate = new Date(tokenCreateDate.getTime() + (long) time * 60 * 1000);
        // 条件1: 当前时间在 token 创建时间之后
        // 条件2: 当前时间在（token 创建时间 + 指定时间段）之前（即指定时间段内可以刷新 token）
        return refreshDate.after(tokenCreateDate) && refreshDate.before(refreshExpirationDate);
    }

//================ public methods ==================

    /**
     * 从 token 中获取登录用户ID
     * @param token JWT 的 token
     * @return 登录用户ID
     */
    public Long getUserIdFromToken(String token){
        Long id = null;
        try{
            // 从 token 中获取 JWT 中的负载
            DecodedJWT decodedJWTFromToken = verify(token);
            // 从负载中获取用户ID
            id = decodedJWTFromToken.getClaim("userId").asLong();
        }catch (Exception ignored){
        }
        return id;
    }


}
