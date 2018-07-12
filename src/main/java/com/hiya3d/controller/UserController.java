package com.hiya3d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiya3d.utils.RestUtils;

/**
 * @author seven sins
 * 2018年7月12日 下午8:52:06
 */
@RestController
public class UserController {

	@Autowired
	RestUtils restUtil;
	
	@GetMapping("/token")
	public Object token() {
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		params.add("client_id", "8401a89b9e1a49573d");
		params.add("client_secret", "fd88f37dbc7c583d52d4baf3d1625dc5");
		params.add("kdt_id", "41087307");
		return restUtil.send("https://uic.youzan.com/sso/open/initToken", params);
	}
	
	@GetMapping("/syncUser")
	public Object syncUser() {
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		params.add("client_id", "8401a89b9e1a49573d");
		params.add("client_secret", "fd88f37dbc7c583d52d4baf3d1625dc5");
		
		return restUtil.send("https://uic.youzan.com/sso/open/login", params);
	}
}
