package com.chengxuxiaoba.video.jwt;

import com.chengxuxiaoba.video.model.JWTToken;
import com.chengxuxiaoba.video.util.JSONUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTTokenFactory {

    @Value("${jwtToken.token-expire-time}")
    private String expire_time;

    @Value("${jwtToken.encrypt-key}")
    private String encrypt_key ;

    private Claims claim;

//    public static JWTTokenFactory getInstance() {
//        if (instance == null)
//            instance = new JWTTokenFactory();
//        return instance;
//    }

    private JWTTokenFactory(){
    }

    public String generateTokenString(JWTToken token)
    {
        String sub = generateSubject(token);
        String tokenStr = generateJWTToken(sub);

        return tokenStr;
    }

    public JWTToken refreshJWTToken(String token) {
        try {
            Claims claim = this.parseJWTToken(token);
            String subject = claim.getSubject();
            JWTToken jwtUserInfo = JSONUtil.deserializeJSONStr(subject, JWTToken.class);
            return jwtUserInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateJWTToken(String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date();
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setIssuedAt(now).setSubject(subject)
                .signWith(signatureAlgorithm, key);

        return builder.compact();
    }

    private String generateSubject(JWTToken token) {

        try {
            if(token == null)
                return null;

            ObjectMapper mapper = new ObjectMapper();

            String result = mapper.writeValueAsString(token);
            return result;
        } catch (Exception ex) {
            //todo log
            return null;
        }
    }

    private SecretKey generalKey() {
        encrypt_key = encrypt_key == null ? "chengxuxiaoba-jwt-key":encrypt_key;
        byte[] encodedKey = Base64.getEncoder().encode(encrypt_key.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES/GCM/NoPadding");
        return key;
    }

    private Claims parseJWTToken(String token) {
        if (StringUtil.isNullOrEmpty(token))
            return null;

        SecretKey key = generalKey();
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }
}
