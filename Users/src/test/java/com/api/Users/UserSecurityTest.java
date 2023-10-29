package com.api.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.api.Users.security.JwtUtil;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {"jwt.signingKey=eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY5ODU5MTY1OSwiaWF0IjoxNjk4NTkxNjU5fQ.nTBQQXTUkT8E0OxzYCqCBaakpq1Z0CQ3_Qqaqg_pqq0"})
public class UserSecurityTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.signingKey}")
    private String signingKey;

    @BeforeEach
    public void setUp() {
        jwtUtil.init();
    }

    @Test
    public void testGenerateToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(username, claims.getSubject());

        long expectedExpirationTime = claims.getIssuedAt().getTime() + (1000 * 60 * 60 * 10);
        assertEquals(expectedExpirationTime, claims.getExpiration().getTime());
    }
}
