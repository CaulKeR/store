package com.qwist.store.service.impl;

import com.qwist.store.model.User;
import com.qwist.store.repository.UserRepository;
import enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MongoAuthUserDetailServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MongoAuthUserDetailService mongoAuthUserDetailService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole(UserRole.ROLE_CUSTOMER);
    }

    @Test
    public void testLoadUserByUsernameWhenUserFoundThenReturnUserDetails() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserDetails userDetails = mongoAuthUserDetailService.loadUserByUsername(user.getUsername());

        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    public void testLoadUserByUsernameWhenUserNotFoundThenThrowException() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> mongoAuthUserDetailService.loadUserByUsername(user.getUsername()));
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }
}
