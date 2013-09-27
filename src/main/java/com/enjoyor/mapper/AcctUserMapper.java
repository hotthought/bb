package com.enjoyor.mapper;

import java.util.Map;

import com.enjoyor.pojo.AcctUser;

public interface AcctUserMapper {
	//查询
	public AcctUser selectAcctUserById(String id);
	public AcctUser selectAcctUserByLoginName(String loginName);
	public Map selectAcctUserByName(String loginName);
}
