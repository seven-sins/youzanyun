package com.hiya3d.mapper;

import com.hiya3d.conf.base.BaseMapper;
import com.hiya3d.po.User;

/**
 * @author seven sins
 * @date 2018年1月6日 下午5:12:45
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据mobile获取用户
	 * @param mobile
	 * @return
	 */
	public User getByMobile(String mobile);
}
