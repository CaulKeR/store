package com.qwist.store.service.impl;

import com.qwist.store.model.User;
import com.qwist.store.repository.UserRepository;
import com.qwist.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/***
 * @author - Kiryl Karpuk
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
