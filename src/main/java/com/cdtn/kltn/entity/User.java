package com.cdtn.kltn.entity;

import com.cdtn.kltn.common.Enums;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Integer statusAccount;
    private String role;
    private String rawPassword;

    @Transient
    private String fullName;
    private String phone;
    private String codeClient;
    private String statusAccountName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return List.of(new SimpleGrantedAuthority(authorities.toString()));
    }

    public User(Long id, String firstName, String lastName, String password, String role, Integer statusAccount, String codeClient, String fullName, String phone, String rawPassword){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.statusAccount = statusAccount;
        this.codeClient = codeClient;
        this.phone = phone;
        this.fullName = fullName;
        this.rawPassword = rawPassword;
        this.statusAccountName = Enums.Status.checkName(statusAccount);
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
