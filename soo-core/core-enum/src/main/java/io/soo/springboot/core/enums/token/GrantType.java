package io.soo.springboot.core.enums.token;

public enum GrantType {

    BEARER("Bearer");

    GrantType(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }

}
