package com.hiya3d.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hiya3d.po.MsgPushEntity;
import com.hiya3d.service.UserService;

/**
 * 积分变更回调接口
 * @author seven sins
 * @datetime 2018年7月13日 上午10:28:51
 */
@RestController
public class CallbackController {
	private static final Logger LOG = Logger.getLogger(CallbackController.class);
	private static final int MODE = 1 ; //服务商
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/point/callback", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public Object pointCallback(@RequestBody MsgPushEntity entity) {
		LOG.info("=============积分变更回调接口参数=============");
		Map<String, Object> res = new HashMap<>();
		res.put("code", 0);
		res.put("msg", "success");
		/**
         *  判断是否为心跳检查消息
         *  1.是则直接返回
         */
        if (entity.isTest()) {
            return res;
        }

        /**
         * 解析消息推送的模式  这步判断可以省略
         * 0-商家自由消息推送 1-服务商消息推送
         * 以服务商 举例
         * 判断是否为服务商类型的消息
         * 否则直接返回
         */
        if (entity.getMode() != MODE ){
            return res;
        }
		
        /**
         * 判断消息是否合法
         * 解析sign
         * MD5 工具类开发者可以自行引入
         */
		//		String sign = MD5.digest(YouzanyunUtil.CLIENT_ID + entity.getMsg() + YouzanyunUtil.CLIENT_SECRET);
		//		if (!sign.equals(entity.getSign())) {
		//			return res;
		//		}

        /**
         * 对于msg 先进行URI解码
         */
		String msg = "";
		try {
			msg = URLDecoder.decode(entity.getMsg(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			LOG.error("=============msg解码出错");
			return res;
		}

        /**
         *  ..........
         *  接下来是一些业务处理
         *  判断当前消息的类型 比如交易
         */
        if ("POINTS".equals(entity.getType())) {
        	try {
        		JSONObject object = JSONObject.parseObject(msg);
        		int amount = object.getIntValue("amount");
        		String mobile = object.getString("mobile");
        		if(StringUtils.isBlank(mobile)) {
        			LOG.error("=============手机号不能为空");
        			return res;
        		}
        		userService.updatePoint(mobile, amount);
        	}catch(Exception e) {
        		LOG.error("=============解析数据出错");
        	}
        }
		
		return res;
	}
	
}
