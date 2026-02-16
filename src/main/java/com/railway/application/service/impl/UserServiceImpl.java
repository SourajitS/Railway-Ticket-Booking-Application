package com.railway.application.service.impl;

import com.railway.application.dto.UserDto;
import com.railway.application.entity.Role;
import com.railway.application.entity.User;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.repository.RoleRepository;
import com.railway.application.repository.UserRepository;
import com.railway.application.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);
        Role role = roleRepository.findByName("ROLE_NORMAL").orElseThrow(() -> new ResourceNotFoundException("Server is not configured"));
        user.getRoles().add(role);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        User savedUser=userRepository.save(user);

        return modelMapper.map(savedUser,UserDto.class);
    }
}
