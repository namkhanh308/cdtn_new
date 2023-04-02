package com.cdtn.kltn.service;

import com.cdtn.kltn.entity.Role;
import com.cdtn.kltn.entity.User;
import com.cdtn.kltn.repository.role.RoleRepository;
import com.cdtn.kltn.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void addRoleToUser(String userName, String roleName) {

        User user = userRepository.findByUserName(userName)
                .orElseThrow();
        Role role = roleRepository.findByName(roleName);
        user.setRole(role.getName());
    }
}
