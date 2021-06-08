package com.mrl.pastry.common.utils;

import com.mrl.pastry.common.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Jwt utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/17
 */
public class JwtUtils {

    @Autowired
    private JwtProperties jwtProperties;

    private Claims extraAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    private <T> T extraClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extraAllClaims(token);
        return resolver.apply(claims);
    }

    public <T> T extraClaims(String token, String key, Class<T> valueType) {
        return extraClaim(token, claims -> claims.get(key, valueType));
    }

    public String extraUserName(String token) {
        return extraClaim(token, Claims::getSubject);
    }

    private Date extraExpiration(String token) {
        return extraClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extraExpiration(token).before(new Date());
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extraUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String generateToken(@Nullable Map<String, Object> claims, @NonNull String subject) {
        Assert.notNull(subject, "jwt-token subject must not be null");
        JwtBuilder builder = Jwts.builder();
        if (!CollectionUtils.isEmpty(claims)) {
            builder.setClaims(claims);
        }
        long now = System.currentTimeMillis();
        return builder.setSubject(subject).setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtProperties.getExpiration() * 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret()).compact();
    }
}
