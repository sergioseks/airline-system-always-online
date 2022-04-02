package com.sergioseks.asao.ms.checkin.services;

import java.util.List;

import com.sergioseks.asao.ms.checkin.models.Checkin;

public interface ICheckinService extends ICRUDService<Checkin> {

	List<Checkin> checkinByTravelerId(int travelerId);
	
}
