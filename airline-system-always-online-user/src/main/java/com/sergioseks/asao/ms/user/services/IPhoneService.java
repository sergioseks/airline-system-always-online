package com.sergioseks.asao.ms.user.services;

import java.util.List;

import com.sergioseks.asao.ms.user.models.Phone;
import com.sergioseks.asao.ms.user.models.User;

public interface IPhoneService extends ICRUDService<Phone> {

	List<Phone> phoneByUser(User user) throws Exception;
}
