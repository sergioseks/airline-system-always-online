package com.sergioseks.asao.ms.user.services;

import com.sergioseks.asao.ms.user.models.User;

public interface IUserService extends ICRUDService<User>{

	User userByEmail(String email);
	
	User login(String email, String password);
}
