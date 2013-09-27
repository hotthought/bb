package com.enjoyor.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyor.dao.AcctUserDao;
import com.enjoyor.pojo.AcctUser;
import com.enjoyor.service.AcctUserService;

@Service
public class AcctUserServiceImpl implements AcctUserService {
	@Autowired
	private AcctUserDao dao;

	@Override
	public AcctUser selectAcctUserById(String id) {
		
		return dao.selectAcctUserById(id);
	}
	
	@Override
	public AcctUser selectAcctUserByLoginName(String loginName) {
	
		return dao.selectAcctUserByLoginName(loginName);
	}

	public AcctUserDao getDao() {
		return dao;
	}

	public void setDao(AcctUserDao dao) {
		this.dao = dao;
	}

	@Override
	public Map selectAcctUserByName(String loginName) {
		
		return dao.selectAcctUserByName(loginName);
	}

}
