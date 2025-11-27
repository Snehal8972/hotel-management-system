//package com.example.Hotel.utill;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.MalformedParameterizedTypeException;
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//@Component
//public class JwtUtil {
//
//    private String generateToken(Map<String,Object>extraClaims, UserDetails details){
//        return Jwts.builder().setClaims(extraClaims).setSubject(details.getPassword())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
//    }
//
//    public String generateToken(UserDetails userDetails){
//        return generateToken(new HashMap<>(),userDetails);
//    }
//
//    public boolean isTokenValid(String token,UserDetails userDetails){
//        final String userName =extractUserName(token);
//        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//
//
//    private Claims extractAllClamis(String token){
//        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
//    }
//
//    public String extractUserName(String token){
//        return extractClaim(token,Claims::getSubject);
//    }
//    private <T> T extractClaim(String token, Function<Claims,T>claimsResolvers){
//        final Claims claims =extractAllClamis(token);
//        return claimsResolvers.apply(claims);
//    }
//    private Date extractExpiration(String token){
//        return extractClaim(token,Claims::getExpiration);
//    }
//
//private boolean isTokenExpired(String token){
//        return extractExpiration(token).before(new Date());
//}
//
//    private Key getSigningKey() {
//        String base64Key ="uR6z+T9Vz0xSgJ0xkX1Yb+G0mT8sD3c9p0qZk7VbPzE=";
//        byte[] keyBytes = Decoders.BASE64.decode(base64Key);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//}
package com.example.Hotel.utill;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // username/email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24)) // 1 day
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Key getSigningKey() {
        String base64Key = "uR6z+T9Vz0xSgJ0xkX1Yb+G0mT8sD3c9p0qZk7VbPzE="; // must be >=256 bits
        byte[] keyBytes = Decoders.BASE64.decode(base64Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
