package com.hiya3d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiya3d.utils.RestUtils;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanCrmCustomerPointsSync;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanScrmCustomerCreate;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanCrmCustomerPointsSyncParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanCrmCustomerPointsSyncResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanScrmCustomerCreateParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanScrmCustomerCreateResult;
import com.youzan.open.sdk.gen.v3_0_1.api.YouzanCrmFansPointsGet;
import com.youzan.open.sdk.gen.v3_0_1.model.YouzanCrmFansPointsGetParams;
import com.youzan.open.sdk.gen.v3_0_1.model.YouzanCrmFansPointsGetResult;

/**
 * @author seven sins
 * 2018年7月12日 下午8:52:06
 */
@RestController
public class UserController {
	/**
	 * 使用client_id和client_secret获取的token
	 */
	private static final String TOKEN = "c9fda8a042ab34db8cd735b395ee31f3";
	@Autowired
	RestUtils restUtil;
	
	/**
	 * 获取token
	 * @return
	 */
	@GetMapping("/token")
	public Object token() {
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		params.add("client_id", "8401a89b9e1a49573d");
		params.add("client_secret", "fd88f37dbc7c583d52d4baf3d1625dc5");
		params.add("kdt_id", "41087307");
		return restUtil.send("https://uic.youzan.com/sso/open/initToken", params);
	}
	
	/**
	 * 登录接口
	 * 		测试用户ID: app_user_id_123
	 * App 用户首次登录有赞时，
	 * 调用 https://uic.youzan.com/sso/open/login 
	 * 将App 用户 id（open_user_id）传递给有赞，有赞将创建一个对应的用户
	 * @return
	 */
	@GetMapping("/syncUser")
	public Object syncUser() {
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		params.add("kdt_id", "41087307");
		params.add("client_id", "8401a89b9e1a49573d");
		params.add("client_secret", "fd88f37dbc7c583d52d4baf3d1625dc5");
		params.add("open_user_id", "app_user_id_123");
		params.add("nick_name", "昵称123");
		params.add("telephone", "18816789926");
		return restUtil.send("https://uic.youzan.com/sso/open/login", params);
	}
	@GetMapping("/syncUser1")
	public Object syncUser1() {
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		params.add("kdt_id", "41087307");
		params.add("client_id", "8401a89b9e1a49573d");
		params.add("client_secret", "fd88f37dbc7c583d52d4baf3d1625dc5");
		params.add("open_user_id", "app_user_id_1235");
		params.add("nick_name", "昵称1235");
		params.add("telephone", "18816789927");
		return restUtil.send("https://uic.youzan.com/sso/open/login", params);
	}
	
	/**
	 * 全量同步用户积分
	 * api接口: youzan.crm.customer.points.sync
	 * @return
	 */
	@GetMapping("/syncPoint")
	public Object syncPoint() { // failure
		@SuppressWarnings("resource")
		YZClient client = new DefaultYZClient(new Token(TOKEN)); //new Sign(appKey, appSecret)
		YouzanCrmCustomerPointsSyncParams youzanCrmCustomerPointsSyncParams = new YouzanCrmCustomerPointsSyncParams();
		youzanCrmCustomerPointsSyncParams.setPoints(6000L);
		youzanCrmCustomerPointsSyncParams.setReason("同步积分");
		youzanCrmCustomerPointsSyncParams.setMobile("18816789926");
		/**
		 * 此处不能传openUserId， openUserId是app开店用户使用的
		 */
		// youzanCrmCustomerPointsSyncParams.setOpenUserId("app_user_id_123");

		YouzanCrmCustomerPointsSync youzanCrmCustomerPointsSync = new YouzanCrmCustomerPointsSync();
		youzanCrmCustomerPointsSync.setAPIParams(youzanCrmCustomerPointsSyncParams);
		YouzanCrmCustomerPointsSyncResult result = client.invoke(youzanCrmCustomerPointsSync);
		
		return result;
	}
	@GetMapping("/syncPoint1")
	public Object syncPoint1() { // failure
		@SuppressWarnings("resource")
		YZClient client = new DefaultYZClient(new Token(TOKEN)); //new Sign(appKey, appSecret)
		YouzanCrmCustomerPointsSyncParams youzanCrmCustomerPointsSyncParams = new YouzanCrmCustomerPointsSyncParams();
		youzanCrmCustomerPointsSyncParams.setPoints(6001L);
		youzanCrmCustomerPointsSyncParams.setReason("同步积分");
		youzanCrmCustomerPointsSyncParams.setMobile("18816789927");
		/**
		 * 此处不能传openUserId， openUserId是app开店用户使用的
		 */
		// youzanCrmCustomerPointsSyncParams.setOpenUserId("app_user_id_123");

		YouzanCrmCustomerPointsSync youzanCrmCustomerPointsSync = new YouzanCrmCustomerPointsSync();
		youzanCrmCustomerPointsSync.setAPIParams(youzanCrmCustomerPointsSyncParams);
		YouzanCrmCustomerPointsSyncResult result = client.invoke(youzanCrmCustomerPointsSync);
		
		return result;
	}
	
	/**
	 * 获取用户积分
	 * api接口: youzan.crm.fans.points.get
	 * @return
	 */
	@GetMapping("/getPoint")
	public Object getPoint() {
		@SuppressWarnings("resource")
		YZClient client = new DefaultYZClient(new Token(TOKEN)); //new Sign(appKey, appSecret)
		YouzanCrmFansPointsGetParams youzanCrmFansPointsGetParams = new YouzanCrmFansPointsGetParams();
		youzanCrmFansPointsGetParams.setMobile("18816789926");
		// youzanCrmFansPointsGetParams.setOpenUserId("app_user_id_123");

		YouzanCrmFansPointsGet youzanCrmFansPointsGet = new YouzanCrmFansPointsGet();
		youzanCrmFansPointsGet.setAPIParams(youzanCrmFansPointsGetParams);
		YouzanCrmFansPointsGetResult result = client.invoke(youzanCrmFansPointsGet);
		
		return result;
	}
	@GetMapping("/getPoint1")
	public Object getPoint1() {
		@SuppressWarnings("resource")
		YZClient client = new DefaultYZClient(new Token(TOKEN)); //new Sign(appKey, appSecret)
		YouzanCrmFansPointsGetParams youzanCrmFansPointsGetParams = new YouzanCrmFansPointsGetParams();
		youzanCrmFansPointsGetParams.setMobile("18816789927");

		YouzanCrmFansPointsGet youzanCrmFansPointsGet = new YouzanCrmFansPointsGet();
		youzanCrmFansPointsGet.setAPIParams(youzanCrmFansPointsGetParams);
		YouzanCrmFansPointsGetResult result = client.invoke(youzanCrmFansPointsGet);
		
		return result;
	}
	
	@GetMapping("/user/create1")
	public Object createUser() {
		@SuppressWarnings("resource")
		YZClient client = new DefaultYZClient(new Token(TOKEN)); //new Sign(appKey, appSecret)
		YouzanScrmCustomerCreateParams youzanScrmCustomerCreateParams = new YouzanScrmCustomerCreateParams();
		youzanScrmCustomerCreateParams.setMobile("18816789927");
		youzanScrmCustomerCreateParams.setCustomerCreate("{\"name\":\"JJBB1\",\"gender\":1,\"birthday\":\"2017-01-01\",\"remark\":\"OK\",\"contact_address\":{\"area_code\":210224,\"address\":\"猴子1\"}}");
		YouzanScrmCustomerCreate youzanScrmCustomerCreate = new YouzanScrmCustomerCreate();
		youzanScrmCustomerCreate.setAPIParams(youzanScrmCustomerCreateParams);
		YouzanScrmCustomerCreateResult result = client.invoke(youzanScrmCustomerCreate);
		
		return result;
	}
}
