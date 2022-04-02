package com.sergioseks.asao.ms.user.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergioseks.asao.ms.user.models.Phone;
import com.sergioseks.asao.ms.user.models.User;
import com.sergioseks.asao.ms.user.repositories.IPhoneRepository;
import com.sergioseks.asao.ms.user.services.IPhoneService;

@Service
public class PhoneService implements IPhoneService {

	@Autowired
	private IPhoneRepository iPhoneRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<Phone> findAll() throws Exception {

		return iPhoneRepository.findAll();
	}

	@Transactional
	@Override
	public Phone save(Phone t) throws Exception {

		return iPhoneRepository.save(t);
	}

	@Transactional
	@Override
	public Phone update(Phone t) throws Exception {

		return iPhoneRepository.save(t);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Phone> findById(Integer id) throws Exception {

		return iPhoneRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {

		iPhoneRepository.deleteById(id);

	}

	@Transactional
	@Override
	public void deleteAll() throws Exception {
		
		iPhoneRepository.deleteAll();

	}

	@Transactional(readOnly = true)
	@Override
	public List<Phone> phoneByUser(User user) throws Exception {

		return iPhoneRepository.phoneByUser(user);
	}

}
