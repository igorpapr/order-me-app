package com.orderme.ordermebackend.repository;

import com.orderme.ordermebackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String userName);

}
