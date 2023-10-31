package com.qwist.store.repository;

import com.qwist.store.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/***
 * @author - Kiryl Karpuk
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
