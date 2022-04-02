package com.sergioseks.asao.ms.user.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergioseks.asao.ms.user.models.User;
import com.sergioseks.asao.ms.user.repositories.IUserRepository;
import com.sergioseks.asao.ms.user.services.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository iUserRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() throws Exception {
		
		return iUserRepository.findAll();
	}

	@Transactional
	@Override
	public User save(User t) throws Exception {

		return iUserRepository.save(t);
	}

	@Transactional
	@Override
	public User update(User t) throws Exception {

		return iUserRepository.save(t);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<User> findById(Integer id) throws Exception {

		return iUserRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		
		iUserRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void deleteAll() throws Exception {
		
		iUserRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	@Override
	public User userByEmail(String email) {

		return iUserRepository.userByEmail(email);
	}

	@Transactional(readOnly = true)
	@Override
	public User login(String email, String password) {
		
		User user = this.userByEmail(email);
		
		if (user != null) {
			
			if (user.getPasssword().equals(password))
				return user;
			else
				user = null;
		}
		
		return user;
	}

}
