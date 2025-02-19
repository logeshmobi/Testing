package com.mobiversa.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.MID;
import com.mobiversa.payment.dao.MidDao;

@Service
public class MidService {
	@Autowired
	private MidDao midDAO;

	public MID loadMidByPk(final Long id) {
		MID mid = midDAO.loadEntityByKey(MID.class, id);
		if (mid == null) {
			throw new RuntimeException("mid Not found. ID:: " + id);
		}
		return mid;
	}
}
