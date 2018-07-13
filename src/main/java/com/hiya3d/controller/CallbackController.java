package com.hiya3d.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hiya3d.po.MsgPushEntity;

/**
 * 积分变更回调接口
 * @author seven sins
 * @datetime 2018年7月13日 上午10:28:51
 */
@RestController
public class CallbackController {

	@RequestMapping(value = "/point/callback", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public Object pointCallback(@RequestBody MsgPushEntity entity) {
		System.out.println("=============积分变更回调接口参数=============");
		System.out.println(JSONObject.toJSON(entity));
		
		Map<String, Object> map = new HashMap<>();
		map.put("code", 0);
		map.put("msg", "success");
		
		return map;
	}
	
}
