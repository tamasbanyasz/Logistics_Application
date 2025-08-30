package hu.tomi.logistic.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String secret = "0123456789ABCDEF0123456789ABCDEF";
    private final long jwtExpirationMs = 600_000; // 10 minutes the expiration time of the token

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractClaims(token);
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<?> rawAuthorities = (List<?>) claims.get("authorities");
            String json = mapper.writeValueAsString(rawAuthorities);

            List<Map<String, String>> authorityMaps = mapper.readValue(
                    json,
                    new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, String>>>() {}
            );

            return authorityMaps.stream()
                    .map(auth -> new SimpleGrantedAuthority(auth.get("authority")))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Could not parse authorities from token", e);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Date expiration = extractClaims(token).getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
