package com.orderme.ordermebackend.utils.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@PropertySource("classpath:application.properties")
public class JwtUtils {

    //private static final String SECRET_KEY_PROP_NAME = "orderme.security.jwt.secret-key";
    private static final int TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 3; //3 hours
    private static final String SECRET_KEY = "SECRET";//getSecretKeyFromProperties();

    /**
     * Creates the JWT token by the given user details
     * @param userDetails - current user details provided by UserDetailsService
     * @return generated JWT token
     */
    public static String generateJWTToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        //claims.put("username",userDetails.getUsername());
        //claims.put("userId",userDetails.getUsername());
        //claims.put("username",userDetails.getUsername());
        //claims.put("username",userDetails.getUsername());
        return createJWTToken(claims, userDetails);
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername() /*!isTokenExpired(token) && */));
    }

    public static String extractUsername(String token) {
        return extractClaimByField(token, Claims::getSubject);
    }

    private static Date extractExpirationDate(String token) {
        return extractClaimByField(token, Claims::getExpiration);
    }

    private static <T> T extractClaimByField(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractClaim(token));
    }

//    private static boolean isTokenExpired(String token) {
//        return extractExpirationDate(token).before(new Date());
//    }

    private static Claims extractClaim(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private static String createJWTToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

//    private static String getSecretKeyFromProperties() {
//        String secret = System.//System.getProperty(SECRET_KEY_PROP_NAME);
//        if (secret == null) {
//            throw new IllegalArgumentException("Couldn't get key: " + SECRET_KEY_PROP_NAME);
//        }
//        return secret;
//    }
}
