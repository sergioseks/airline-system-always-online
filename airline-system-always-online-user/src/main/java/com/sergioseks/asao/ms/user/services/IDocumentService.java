package com.sergioseks.asao.ms.user.services;

import java.util.List;

import com.sergioseks.asao.ms.user.models.Document;
import com.sergioseks.asao.ms.user.models.User;

public interface IDocumentService extends ICRUDService<Document>{

	List<Document> documentByUser (User user) throws Exception;
	
}
