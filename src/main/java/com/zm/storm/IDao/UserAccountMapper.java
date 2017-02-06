package com.zm.storm.IDao;

import com.zm.storm.domain.UserAccount;

public interface UserAccountMapper {
    int deleteByPrimaryKey(long id);

    int insert(UserAccount record);

    int insertSelective(UserAccount record);

    UserAccount selectByPrimaryKey(long id);

    int updateByPrimaryKeySelective(UserAccount record);

    int updateByPrimaryKey(UserAccount record);
}