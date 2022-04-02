package com.sergioseks.asao.ms.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sergioseks.asao.ms.user.models.Phone;
import com.sergioseks.asao.ms.user.models.User;

@Repository
public interface IPhoneRepository extends JpaRepository<Phone, Integer> {

	List<Phone> phoneByUser(User user);
	
}
