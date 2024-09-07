package org.cristianvelasquezp.springsecuritypractice.config;

import org.cristianvelasquezp.springsecuritypractice.entities.UserEntity;
import org.cristianvelasquezp.springsecuritypractice.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SSPracticeUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public SSPracticeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User details not found for the user: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();

        userEntity.getAuthorities().forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        });

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }
}
