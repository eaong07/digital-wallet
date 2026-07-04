package com.digital.wallet.user_service.service;

import com.digital.wallet.user_service.dto.UserDto;
import com.digital.wallet.user_service.entity.User;
import com.digital.wallet.user_service.repository.UserRepository;
import com.digital.wallet.user_service.util.UserMapper;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDto getUser(String id) {
        Optional<User> user = userRepository.findById(id);

        return user.
                map(userMapper::toDto).orElse(null);
    }

    public void addUser(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
    }

    public void updateUser(UserDto userDto) {
        Optional<User> result = userRepository.findById(userDto.getUserId());
        User user = result.orElse(null);
        if (Objects.nonNull(user)) {
            user = userMapper.toEntity(userDto);
            userRepository.save(user);
        }
    }

}
