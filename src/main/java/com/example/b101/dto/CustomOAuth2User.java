package com.example.b101.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final UserDto userdto;

    public CustomOAuth2User(UserDto userdto) {
        this.userdto = userdto;
    }

    @Override
    public Map<String, Object> getAttributes() {
        // OAuth2 인증에서 전달된 사용자 정보를 반환
        return Map.of(
                "email", userdto.getEmail(),
                "name", userdto.getName(),
                "username", userdto.getUsername()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            public String getEmail(){
                return userdto.getEmail();
            }

            @Override
            public String getAuthority() {

                return userdto.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        System.out.println("여기가 getName!!!!!!!!!");
        System.out.println(userdto);
        System.out.println(userdto.getName());
        System.out.println(userdto.getEmail());
        return userdto.getEmail();
    }

    public String getUsername() {

        return userdto.getUsername();
    }


}
