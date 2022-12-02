package com.palchil.shop.service;

import com.palchil.shop.domain.User;
import com.palchil.shop.repository.UserRepository;
import com.palchil.shop.web.dto.user.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public User login(LoginDto loginDto) {
        try {
            User findUser = userRepository.findById(loginDto.getId());
            if (Objects.equals(findUser.getPw(), loginDto.getPw())) {
                return findUser;
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
}
