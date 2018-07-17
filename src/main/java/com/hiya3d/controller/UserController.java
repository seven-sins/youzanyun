package com.hiya3d.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hiya3d.conf.base.BaseController;
import com.hiya3d.conf.base.Response;
import com.hiya3d.conf.exception.HiyaException;
import com.hiya3d.po.User;
import com.hiya3d.service.UserService;
import com.hiya3d.utils.YouzanyunUtil;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanCrmCustomerPointsSync;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanScrmCustomerCreate;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanCrmCustomerPointsSyncParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanCrmCustomerPointsSyncResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanScrmCustomerCreateParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanScrmCustomerCreateResult;

/**
 * @author seven sins
 * @datetime 2018年7月14日 下午11:21:28
 */
@RestController
public class UserController extends BaseController {
	private static final Logger LOG = Logger.getLogger(UserController.class);
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
	
	@SuppressWarnings("resource")
	@GetMapping("/rest/user/{mobile}/sync-to-shop")
	public Response<?> sync(@PathVariable("mobile") String mobile){
		User user = userService.getByMobile(mobile);
		if(user == null) {
			throw new HiyaException("用户未找到");
		}
		YZClient client = new DefaultYZClient(new Token(YouzanyunUtil.TOKEN)); //new Sign(appKey, appSecret)
		YouzanScrmCustomerCreateParams youzanScrmCustomerCreateParams = new YouzanScrmCustomerCreateParams();
		youzanScrmCustomerCreateParams.setMobile(mobile);
		JSONObject json = new JSONObject();
		json.put("name", user.getNickName());
		json.put("gender", 1);
		json.put("birthday", "2018-01-01");
		json.put("remark", "test");
		json.put("contact_address", "{\"area_code\":510000,\"address\":\"宁静之森\"}");
		youzanScrmCustomerCreateParams.setCustomerCreate(json.toJSONString());
		YouzanScrmCustomerCreate youzanScrmCustomerCreate = new YouzanScrmCustomerCreate();
		youzanScrmCustomerCreate.setAPIParams(youzanScrmCustomerCreateParams);
		LOG.info("=============同步用户:");
		try {
			YouzanScrmCustomerCreateResult result = client.invoke(youzanScrmCustomerCreate);
			LOG.info(result);
		}catch(Exception e) {
			LOG.info("=============同步用户出错:");
			LOG.error(e);
		}
		
		LOG.info("=============同步积分:");
		client = new DefaultYZClient(new Token(YouzanyunUtil.TOKEN)); //new Sign(appKey, appSecret)
		YouzanCrmCustomerPointsSyncParams youzanCrmCustomerPointsSyncParams = new YouzanCrmCustomerPointsSyncParams();
		youzanCrmCustomerPointsSyncParams.setPoints((long)user.getPoint());
		youzanCrmCustomerPointsSyncParams.setReason(YouzanyunUtil.SYNC_POINT_DESC);
		youzanCrmCustomerPointsSyncParams.setMobile(mobile);
		YouzanCrmCustomerPointsSync youzanCrmCustomerPointsSync = new YouzanCrmCustomerPointsSync();
		youzanCrmCustomerPointsSync.setAPIParams(youzanCrmCustomerPointsSyncParams);
		YouzanCrmCustomerPointsSyncResult result1 = client.invoke(youzanCrmCustomerPointsSync);
		LOG.info(JSONObject.toJSONString(result1));
		
		return Response.SUCCESS;
	}
}
