package com.zm.storm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zm.storm.IDao.UserAccountMapper;
import com.zm.storm.domain.UserAccount;
import com.zm.storm.service.IUserAccountService;

@Service("userAccountService")
public class UserAccountServiceImpl implements IUserAccountService {
 
	@Resource
	private UserAccountMapper userAccountDao;

	public UserAccount getUserAccountById(long userId) {
		return this.userAccountDao.selectByPrimaryKey(userId);
	}

	@Override
	public void updateUserAccountById(long id) {
		
		UserAccount record = new UserAccount();
		record.setId(1l);
		record.setUserName("zhangmeng");
		record.setUserPwd("zm521521");
		System.out.println(record.toString());
		this.userAccountDao.updateByPrimaryKey(record);
	}

	@Override
	public int addUserAccount(final UserAccount ua) {
		return this.userAccountDao.insert(ua);
	}
	
}
