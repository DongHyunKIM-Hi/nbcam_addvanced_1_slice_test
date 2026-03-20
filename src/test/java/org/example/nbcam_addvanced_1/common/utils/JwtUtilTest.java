package org.example.nbcam_addvanced_1.common.utils;

import static org.assertj.core.api.Assertions.assertThat;
import io.jsonwebtoken.Claims;
import org.example.nbcam_addvanced_1.common.enums.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkeymysecretkey";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();

        // 테스트용 secret key (Base64 인코딩된 256bit 키)
        String testSecretKey = "c2VjdXJldGVzdGtleW15c2VjdXJldGVzdGtleW15c2VjdXJldGVzdGtleQ==";

        // Reflection을 이용해 @Value 주입 대체
        ReflectionTestUtils.setField(jwtUtil, "secretKeyString", testSecretKey);

        // @PostConstruct 수동 호출
        jwtUtil.init();
    }

    @Test
    @DisplayName("JWT 토큰 생성 시 username과 role 정보가 정상적으로 포함된다")
    void generateToken_정상생성_Claims확인() {
        // Given
        String username = "ravi";
        UserRoleEnum role = UserRoleEnum.ADMIN;

        // When
        String tokenWithPrefix = jwtUtil.generateToken(username, role);
        String pureToken = tokenWithPrefix.substring(JwtUtil.BEARER_PREFIX.length());

        // Then
        assertThat(tokenWithPrefix).startsWith("Bearer ");

        Claims claims = jwtUtil.getParser()
            .parseSignedClaims(pureToken)
            .getPayload();

        assertThat(claims.get("username", String.class)).isEqualTo(username);
        assertThat(claims.get("auth", String.class)).isEqualTo(role.name());
        assertThat(claims.getExpiration()).isAfter(new java.util.Date());
    }

    @Test
    @DisplayName("유효한 토큰은 validateToken()에서 true를 반환한다")
    void validateToken_정상토큰_true반환() {
        // Given
        String token = jwtUtil.generateToken("ravi", UserRoleEnum.ADMIN)
            .substring(JwtUtil.BEARER_PREFIX.length());

        // When
        boolean isValid = jwtUtil.validateToken(token);

        // Then
        assertThat(isValid).isTrue();
    }


    @Test
    @DisplayName("잘못된 토큰은 validateToken()에서 false를 반환한다")
    void validateToken_잘못된토큰_false반환() {
        // Given
        String invalidToken = "invalid.jwt.token";

        // When
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // Then
        assertThat(isValid).isFalse();
    }

}