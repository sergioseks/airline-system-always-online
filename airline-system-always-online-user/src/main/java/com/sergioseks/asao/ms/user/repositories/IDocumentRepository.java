package com.sergioseks.asao.ms.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sergioseks.asao.ms.user.models.Document;
import com.sergioseks.asao.ms.user.models.User;

@Repository
public interface IDocumentRepository extends JpaRepository<Document, Integer> {

	List<Document> documentByUser (User user);
	
}
