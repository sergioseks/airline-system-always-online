package com.sergioseks.asao.ms.checkin.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergioseks.asao.ms.checkin.models.Checkin;
import com.sergioseks.asao.ms.checkin.repositories.ICheckinRepository;
import com.sergioseks.asao.ms.checkin.services.ICheckinService;

@Service
public class CheckinService implements ICheckinService {

	@Autowired
	private ICheckinRepository iCheckinRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<Checkin> findAll() throws Exception {

		return iCheckinRepository.findAll();
	}

	@Transactional
	@Override
	public Checkin save(Checkin t) throws Exception {

		return iCheckinRepository.save(t);
	}

	@Transactional
	@Override
	public Checkin update(Checkin t) throws Exception {

		return iCheckinRepository.save(t);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Checkin> findById(Integer id) throws Exception {

		return iCheckinRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		
		iCheckinRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void deleteAll() throws Exception {
		
		iCheckinRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Checkin> checkinByTravelerId(int travelerId) {

		return iCheckinRepository.checkinByTravelerId(travelerId);
	}

}
