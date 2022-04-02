package com.sergioseks.asao.ms.checkin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sergioseks.asao.ms.checkin.models.Checkin;

@Repository
public interface ICheckinRepository extends JpaRepository<Checkin, Integer>{

	List<Checkin> checkinByTravelerId(int travelerId);
	
}
