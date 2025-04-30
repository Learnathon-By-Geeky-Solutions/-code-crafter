package com.xenon.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.exception.AuthException;
import com.xenon.core.domain.model.ResponseMessage;
import com.xenon.data.entity.user.User;
import com.xenon.data.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.validity}")
    private Long tokenValidityDays;

    private final ObjectMapper objectMapper;
    private final UserRepository repository;

    public String generateAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        setUpClaims(claims, username);
        return generateAccessToken(claims, username);
    }

    public boolean isLongSession(String token) {
        return getClaimsFromToken(token).get("isLongSession", Boolean.class);
    }

    public boolean isExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    @SneakyThrows
    private void setUpClaims(Map<String, Object> claims, String username) {
        User user = repository.findByPhone(username).orElseThrow(() -> new AuthException("Invalid token provided."));
        claims.put("jwt-user", objectMapper.writeValueAsString(user));
    }

    private String generateAccessToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuer("Xenon")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityDays * 24 * 60 * 60 * 1000))
                .signWith(getSigningKey())
                .compact();
    }

    @SneakyThrows
    public User getCurrentUser(String token) {
        String userJson = (String) getClaimsFromToken(token).get("jwt-user");
        return objectMapper.readValue(userJson, User.class);
    }

    public String getCurrentUserPhone(String token) {
        return getCurrentUser(token).getPhone();
    }

    public String getCurrentUserEmail(String token) {
        return getCurrentUser(token).getEmail();
    }

    public Long getCurrentUserId(String token) {
        return getCurrentUser(token).getId();
    }

    @NonNull
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    private Date getExpirationDateFromToken(String token) {
        try {
            return getClaimFromToken(token, Claims::getExpiration);
        } catch (ExpiredJwtException e) {
            throw new AuthException(ResponseMessage.SESSION_EXPIRED);
        }
    }

    private <T> T getClaimFromToken(String token, @NonNull Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getClaimsFromToken(token));
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}