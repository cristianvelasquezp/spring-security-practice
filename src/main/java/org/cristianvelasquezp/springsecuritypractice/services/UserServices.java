package org.cristianvelasquezp.springsecuritypractice.services;

import org.cristianvelasquezp.springsecuritypractice.entities.UserEntity;
import org.cristianvelasquezp.springsecuritypractice.exceptions.UserEmailException;
import org.cristianvelasquezp.springsecuritypractice.mappers.UserMapper;
import org.cristianvelasquezp.springsecuritypractice.models.UserRequest;
import org.cristianvelasquezp.springsecuritypractice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServices {

    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PasswordEncoder passwordEncoder;

    public UserServices(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(UserRequest user) throws UserEmailException {

        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new UserEmailException("User already exists");

        UserEntity userEntity = userMapper.userRequestToUserEntity(user);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
