package io.soo.springboot.core.enums.member;

public enum MemberRole {

    USER, ADMIN;

    public static MemberRole from(String role) {
        return MemberRole.valueOf(role);
    }

}
