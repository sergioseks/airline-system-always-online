package com.sergioseks.asao.ms.user.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergioseks.asao.ms.user.models.Document;
import com.sergioseks.asao.ms.user.models.User;
import com.sergioseks.asao.ms.user.repositories.IDocumentRepository;
import com.sergioseks.asao.ms.user.services.IDocumentService;

@Service
public class DocumentService implements IDocumentService {

	@Autowired
	private IDocumentRepository iDocumentRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<Document> findAll() throws Exception {

		return iDocumentRepository.findAll();
	}

	@Transactional
	@Override
	public Document save(Document t) throws Exception {

		return iDocumentRepository.save(t);
	}

	@Transactional
	@Override
	public Document update(Document t) throws Exception {

		return iDocumentRepository.save(t);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Document> findById(Integer id) throws Exception {

		return iDocumentRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		
		iDocumentRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void deleteAll() throws Exception {
		
		iDocumentRepository.deleteAll();

	}

	@Transactional(readOnly = true)
	@Override
	public List<Document> documentByUser(User user) throws Exception {

		return iDocumentRepository.documentByUser(user);
	}

}
