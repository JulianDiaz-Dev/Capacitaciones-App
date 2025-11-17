package com.example.capacitaciones.security;

import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.capacitaciones.model.Rol;
import com.example.capacitaciones.model.Usuario;

import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret; // se setea desde application.yml

    @Value("${jwt.expiration-ms}")
    private long expiration; // en ms

    public String generateToken(Usuario user) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        List<String> roleNames = user.getRoles().stream()
                             .map(Rol::getNombre)
                             .toList();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", roleNames)
                .claim("ContraseniaTemporal", user.isContraseniaTemporal())
                .claim("idUsuario", user.getIdUsuario())
                .claim("nombre", user.getNombre())
                .claim("apellido", user.getApellido())
                .claim("email", user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
