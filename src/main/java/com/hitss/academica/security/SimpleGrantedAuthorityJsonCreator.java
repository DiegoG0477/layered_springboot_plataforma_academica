package com.hitss.academica.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// Clase abstracta mix-in para ayudar a Jackson a deserializar SimpleGrantedAuthority
public abstract class SimpleGrantedAuthorityJsonCreator {

    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
    }
}