package com.telusko.demoApp.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private String secretKey  = "";

    public JWTService() {
        try {
                                    //which algorithm are we using to generate token
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            //Convert the key to String
            secretKey =  Base64.getEncoder().encodeToString(sk.getEncoded());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {
        Map<String , Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()//Claims generate krr rhe
                .add(claims)//Unn claims ko MAP mein add krr rhe
                .subject(username)//Unn claims ka subject Username rhega
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
                .and()
                .signWith(getKey())
                .compact();

    }

    public SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);//Convert the String Key into Bytes
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //Kuch Compulsary methods jo hrr baar likhne padenge
    public String extractUserName(String token) {
        return extractClaim(token , Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims =  extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token , Claims::getExpiration);
    }

}
