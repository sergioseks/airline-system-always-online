package com.sergioseks.asao.ms.booking.services;

import java.util.List;
import java.util.Optional;

public interface ICRUDService <T>{

	List<T> findAll() throws Exception;
	
	T save(T booking) throws Exception;
	
	Optional<T> findById(Integer id) throws Exception;
	
	void deleteById(Integer id) throws Exception;
	
	void deleteAll() throws Exception;
}
