package com.github.geekuniversity_java_215.cmsbackend.protocol.token;

import java.util.HashMap;
import java.util.Map;

public enum TokenType {



    ACCESS("access_token", 3600),
    REFRESH("refresh_token", 3600*24*30),
    CONFIRM("confirm_token", 3600*24);

    private static final Map<String, TokenType> values = new HashMap<>();

    static {
        for (TokenType t :TokenType.values()) {
            values.put(t.name, t);
        }
    }

    private Long ttl;
    private String name;


    TokenType(String name, long ttl) {
        this.ttl = ttl;
        this.name = name;
    }

    /**
     * In seconds
     */
    public long getTtl() {
        return ttl;
    }

    public String getName() {
        return name;
    }

    public static TokenType getByName(String name) {

        if (!values.containsKey(name)) {
            throw new IllegalArgumentException("Token by name " + name + "not found");
        }
        return values.get(name);
    }



}
