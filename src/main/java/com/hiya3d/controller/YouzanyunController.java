package com.hiya3d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hiya3d.po.User;
import com.hiya3d.service.UserService;
import com.hiya3d.utils.RestUtils;
import com.hiya3d.utils.YouzanyunUtil;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_1.api.YouzanCrmFansPointsGet;
import com.youzan.open.sdk.gen.v3_0_1.model.YouzanCrmFansPointsGetParams;
import com.youzan.open.sdk.gen.v3_0_1.model.YouzanCrmFansPointsGetResult;

/**
 * @author seven sins
 * 2018年7月12日 下午8:52:06
 */
@RestController
public class YouzanyunController {
	@Autowired
	RestUtils restUtil;
	@Autowired
	UserService userService;
	
	/**
	 * 获取token
	 * @return
	 */
	@GetMapping("/rest/token")
	public Object token() {
		/**
		 * 	文档地址: https://www.youzanyun.com/docs/guide/3399/3414
		 * 
		 *  1、请求方式:
			Content-Type：application/x-www-form-urlencoded 请求URL：https://open.youzan.com/oauth/token
			
			2、请求参数:
			名称	类型	是否必须	示例值	描述
			client_id		String	是	Test client	有赞云颁发给开发者的应用ID
			client_secret	String	是	Testclientsecret	有赞云颁发给开发者的应用secret
			grant_type		String	是	silent	授与方式（固定为 “silent”）
			kdt_id			Number	是	123456	授权给该应用的店铺id，
		 */
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		params.add("client_id", YouzanyunUtil.CLIENT_ID);
		params.add("client_secret", YouzanyunUtil.CLIENT_SECRET);
		params.add("grant_type", "silent");
		params.add("kdt_id", "41150775");
		return restUtil.send("https://open.youzan.com/oauth/token", params);
	}
	
	@GetMapping("/rest/getPoint/{mobile}")
	public Object getPoint(@PathVariable("mobile") String mobile) {
		User user = userService.getByMobile(mobile);
		
		@SuppressWarnings("resource")
		YZClient client = new DefaultYZClient(new Token(YouzanyunUtil.TOKEN)); //new Sign(appKey, appSecret)
		YouzanCrmFansPointsGetParams youzanCrmFansPointsGetParams = new YouzanCrmFansPointsGetParams();
		youzanCrmFansPointsGetParams.setOpenUserId(user.getUserId());

		YouzanCrmFansPointsGet youzanCrmFansPointsGet = new YouzanCrmFansPointsGet();
		youzanCrmFansPointsGet.setAPIParams(youzanCrmFansPointsGetParams);
		YouzanCrmFansPointsGetResult result = client.invoke(youzanCrmFansPointsGet);
		
		return result;
	}
}
