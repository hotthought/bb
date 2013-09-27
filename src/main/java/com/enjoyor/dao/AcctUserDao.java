package com.enjoyor.dao;

import java.util.Map;

import com.enjoyor.pojo.AcctUser;

public interface AcctUserDao {
	//查询
		public AcctUser selectAcctUserById(String id);
		public AcctUser selectAcctUserByLoginName(String loginName);
		public Map selectAcctUserByName(String loginName);
}
