package com.hiya3d.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @author seven sins
 * @datetime 2018年7月14日 下午11:21:28
 */
@RestController
public class UserController extends BaseController {

	@Autowired
	UserService userService;

	@PostMapping("/rest/user")
	public Response<?> create(@Valid @RequestBody User user) {
		User query = new User();
		query.setMobile(user.getMobile());
		List<User> list = userService.find(query);
		if (list != null && !list.isEmpty()) {
			throw new HiyaException(400, "手机号已存在");
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
			throw new HiyaException(400, "用户不存在");
		}
		return new Response<User>().data(user);
	}

	@PutMapping("/rest/user/{userId}")
	public Response<?> update(@PathVariable("userId") String userId, @RequestBody User user) {
		if (userService.get(userId) == null) {
			throw new HiyaException(400, "用户不存在");
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
}
