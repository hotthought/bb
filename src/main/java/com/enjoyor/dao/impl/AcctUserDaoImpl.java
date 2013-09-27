package com.enjoyor.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.enjoyor.dao.AcctUserDao;
import com.enjoyor.mapper.AcctUserMapper;
import com.enjoyor.pojo.AcctUser;


@Repository
public class AcctUserDaoImpl implements AcctUserDao {
	@Autowired
	private AcctUserMapper mapper;

	@Override
	public AcctUser selectAcctUserById(String id) {
		
		return mapper.selectAcctUserById(id);
	}

	@Override
	public AcctUser selectAcctUserByLoginName(String loginName) {
		
		return mapper.selectAcctUserByLoginName(loginName);
	}

	
	public AcctUserMapper getMapper() {
		return mapper;
	}

	public void setMapper(AcctUserMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public Map selectAcctUserByName(String loginName) {
		
		return mapper.selectAcctUserByName(loginName);
	}

}
