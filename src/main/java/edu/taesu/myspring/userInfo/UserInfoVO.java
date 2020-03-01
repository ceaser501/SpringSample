package edu.taesu.myspring.userInfo;

import java.io.Serializable;
import java.util.Date;

public class UserInfoVO implements Serializable {

	private String userId;
	private String userPw;
	private String userNm;
	private String userEmail;
	private Integer userPoint;
	private String sessionKey;
	private String sessionLimit;
	private String regDate;
	private String modDate;
	private String userJoinDate;
	private String userLoginDate;
	private String userIntro;
	private Date initDate;
	private Integer failCnt;
	private String lockFlag;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserNm() {return userNm;  }
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Integer getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(Integer userPoint) {
		this.userPoint = userPoint;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getSessionLimit() {
		return sessionLimit;
	}
	public void setSessionLimit(String sessionLimit) {
		this.sessionLimit = sessionLimit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getUserJoinDate() {
		return userJoinDate;
	}
	public void setUserJoinDate(String userJoinDate) {
		this.userJoinDate = userJoinDate;
	}
	public String getUserLoginDate() {
		return userLoginDate;
	}
	public void setUserLoginDate(String userLoginDate) {
		this.userLoginDate = userLoginDate;
	}
	public String getUserIntro() {return userIntro; }
	public void setUserIntro(String userIntro) { this.userIntro = userIntro; }

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModDate() {
		return modDate;
	}

	public void setFailCnt(Integer failCnt) {
		this.failCnt = failCnt;
	}

	public Integer getFailCnt() {
		return failCnt;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getLockFlag() {
		return lockFlag;
	}
}
