package com.chengxuxiaoba.video.jwt;

import com.chengxuxiaoba.video.model.JWTToken;
import com.chengxuxiaoba.video.util.JSONUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

public class JWTTokenFactory {
    @Autowired
    private Environment env;

    private static JWTTokenFactory instance;
    private long expire_time = 1800000;//Long.parseLong(env.getProperty("token-expire-time", "1800000"));

    private String encrypt_key = "vtd system encryption key";// env.getProperty("jwt-encrypt-key");

    private Claims claim;

    public static JWTTokenFactory getInstance() {
        if (instance == null)
            instance = new JWTTokenFactory();
        return instance;
    }

    public static void main(String[] args) {
        JWTToken token = new JWTToken();
        token.setStatus("1");
        token.setRole("admin");
        token.setName("lucy");
        token.setUserId("as");
        token.setWechat_account("qwert");

        String tokenStr = JWTTokenFactory.getInstance().generateTokenString(token);

        System.out.println(tokenStr);

        JWTToken jwtUserInfo = JWTTokenFactory.getInstance().refreshJWTToken(tokenStr);

        System.out.println(jwtUserInfo);
    }

    private JWTTokenFactory() {
    }

    public String generateTokenString(JWTToken token)
    {
        String sub = JWTTokenFactory.getInstance().generateSubject(token);
        String tokenStr = JWTTokenFactory.getInstance().generateJWTToken(sub);

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

            JWTToken jwtUserInfo = new JWTToken();
            jwtUserInfo.setName(token.getName());
            jwtUserInfo.setRole(token.getRole());
            jwtUserInfo.setStatus(token.getStatus());
            jwtUserInfo.setUserId(token.getUserId());
            jwtUserInfo.setWechat_account(token.getWechat_account());

            ObjectMapper mapper = new ObjectMapper();

//            ObjectNode node = mapper.createObjectNode();
//            node.put(USER_ID, token.getUserId());
//            node.put(NAME, token.getName());
//            node.put(WECHAT_ACCOUNT, token.getWechat_account());
//            node.put(STATUS, token.getStatus());
//            node.put(ROLE, token.getRole());

//            String result = mapper.writeValueAsString(node);
            String result = mapper.writeValueAsString(jwtUserInfo);
            return result;
        } catch (Exception ex) {
            //todo log
            return null;
        }
    }

    private SecretKey generalKey() {
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
