package org.example.nbcam_addvanced_1.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {
    ADMIN("ROLE_ADMIN", "관리자 권한"),
    NORMAL("ROLE_NORMAL", "일반권한")
    ;

    private final String role;        // Spring Security 권한 이름
    private final String description; // 권한 설명
}
