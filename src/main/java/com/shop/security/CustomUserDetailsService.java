package com.shop.security;

import com.shop.model.CustomUserDetails;
import com.shop.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private IUserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot load user by username: " + username));
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    public boolean existUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public int updateToken(String username, String token) {
        Optional<CustomUserDetails> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            CustomUserDetails user = userOptional.get();
            user.setToken(token);
            userRepository.save(user);

            return 1;
        } else {
            // Handle user not found scenario
            throw new RuntimeException("Cannot update token with username: " + username);
        }
    }

    public String getTokenByUsername(String username) {
        Optional<CustomUserDetails> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            CustomUserDetails user = userOptional.get();
            return user.getToken();
        } else {
            // Handle user not found scenario
            throw new RuntimeException("Cannot get token by username: " + username);
        }
    }
}