package com.enjoyor.service;

import java.util.Map;

import com.enjoyor.pojo.AcctUser;

public interface AcctUserService {
	//查询
			public AcctUser selectAcctUserById(String id);
			public AcctUser selectAcctUserByLoginName(String loginName);
			public Map selectAcctUserByName(String loginName);
}
