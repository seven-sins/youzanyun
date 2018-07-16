package com.hiya3d.service;

import com.hiya3d.conf.base.BaseService;
import com.hiya3d.po.User;

public interface UserService extends BaseService<User>{

	/**
	 * 更新积分
	 * @param mobile
	 * @param amount
	 */
	public void updatePoint(String mobile, Integer amount);
	
	/**
	 * 根据mobile获取用户
	 * @param mobile
	 * @return
	 */
	public User getByMobile(String mobile);
}
