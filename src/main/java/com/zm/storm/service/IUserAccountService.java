package com.zm.storm.service;

import com.zm.storm.domain.UserAccount;

public interface IUserAccountService {

	public UserAccount getUserAccountById(long id);

	public void updateUserAccountById(long id);
	
	public int addUserAccount(final UserAccount ua);
	
}
