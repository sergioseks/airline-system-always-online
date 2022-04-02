package com.sergioseks.asao.ms.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sergioseks.asao.ms.user.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

	User userByEmail(String email);
}
