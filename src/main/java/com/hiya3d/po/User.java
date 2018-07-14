package com.hiya3d.po;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author seven sins
 * @datetime 2018年7月14日 下午10:56:52
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名
	 */
	@NotBlank(message = "username不能为空")
	private String username;
	/**
	 * 手机号
	 */
	@NotBlank(message = "mobile不能为空")
	private String mobile;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	@NotBlank(message = "nickName不能为空")
	private String nickName;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 积分
	 */
	private Integer point;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	
}
