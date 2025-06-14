package com.p6FullStack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.p6FullStack.model.Users;

@Repository
public interface UsersRepository extends JpaRepository <Users, Integer> {
	Users findByName(String name);
	Users findByEmail(String email);
}
