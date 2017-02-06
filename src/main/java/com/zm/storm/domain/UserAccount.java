package com.zm.storm.domain;

public class UserAccount {
    private Long id;

    private String userName;

    private String userPwd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", userName=" + userName + ", userPwd=" + userPwd + "]";
	}
    
}