package com.hiya3d.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiya3d.conf.base.BaseController;
import com.hiya3d.conf.base.Response;
import com.hiya3d.conf.exception.HiyaException;
import com.hiya3d.po.User;
import com.hiya3d.service.UserService;
import com.hiya3d.utils.RestUtils;
import com.hiya3d.utils.YouzanyunUtil;

/**
 * @author seven sins
 * @datetime 2018年7月14日 下午11:21:28
 */
@RestController
public class UserController extends BaseController {
	private static final Logger LOG = Logger.getLogger(UserController.class);
	@Autowired
	RestUtils restUtil;
	@Autowired
	UserService userService;

	@PostMapping("/rest/user")
	public Response<?> create(@Valid @RequestBody User user) {
		User query = new User();
		query.setMobile(user.getMobile());
		List<User> list = userService.find(query);
		if (list != null && !list.isEmpty()) {
			throw new HiyaException("手机号已存在");
		}
		userService.insert(user);

		return Response.SUCCESS;
	}
	
	@GetMapping("/rest/user-list")
	public Response<User> list(){
		List<User> list = userService.find(null);
		return new Response<User>().list(list);
	}
	
	@GetMapping("/rest/user/{userId}")
	public Response<User> get(@PathVariable("userId") String userId){
		User user = userService.get(userId);
		if(user == null) {
			throw new HiyaException("用户未找到");
		}
		return new Response<User>().data(user);
	}
	
	@GetMapping("/rest/user/{mobile}/mobile")
	public Response<User> getByMobile(@PathVariable("mobile") String mobile){
		User user = userService.getByMobile(mobile);
		if(user == null) {
			throw new HiyaException("用户未找到");
		}
		return new Response<User>().data(user);
	}

	@PutMapping("/rest/user/{userId}")
	public Response<?> update(@PathVariable("userId") String userId, @RequestBody User user) {
		if (userService.get(userId) == null) {
			throw new HiyaException("用户不存在");
		}
		user.setUserId(userId);
		userService.update(user);

		return Response.SUCCESS;
	}

	@DeleteMapping("/rest/user/{userId}")
	public Response<?> delete(@PathVariable("userId") String userId) {
		userService.deleteById(userId);

		return Response.SUCCESS;
	}
	
	@GetMapping("/rest/sync/{mobile}")
	public Object sync(@PathVariable("mobile") String mobile) {
		User user = userService.getByMobile(mobile);
		if(user == null) {
			throw new HiyaException("用户未找到");
		}
		LOG.info("=============自动登录:");
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		params.add("kdt_id", "41150775");
		params.add("client_id", YouzanyunUtil.CLIENT_ID);
		params.add("client_secret", YouzanyunUtil.CLIENT_SECRET);
		params.add("nick_name", user.getNickName());
		params.add("telephone", user.getMobile());
		params.add("open_user_id", user.getUserId());
		Object token = restUtil.send("https://uic.youzan.com/sso/open/login", params);
		LOG.info(token);
		
		LOG.info("=============同步积分:");
		params= new LinkedMultiValueMap<String, String>();
		params.add("access_token", YouzanyunUtil.TOKEN);
		params.add("open_user_id", user.getUserId());
		params.add("points", String.valueOf(user.getPoint()));
		params.add("reason", YouzanyunUtil.SYNC_POINT_DESC);
		Object result = restUtil.send("https://open.youzan.com/api/oauthentry/youzan.crm.customer.points/3.0.0/sync", params);
		LOG.info(result);
		
		return token;
	}
}
