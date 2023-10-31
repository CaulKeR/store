package com.qwist.store.service;

import com.qwist.store.model.User;

import java.util.Optional;

/***
 * @author - Kiryl Karpuk
 */
public interface UserService {

    Optional<User> findByUsername(String username);

}
