package com.hiya3d.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanCrmCustomerPointsIncrease;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanItemGet;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanScrmCustomerCreate;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanCrmCustomerPointsIncreaseResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanScrmCustomerCreateParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanScrmCustomerCreateResult;
import com.youzan.open.sdk.gen.v3_0_1.model.YouzanCrmCustomerPointsIncreaseParams;

/**
 * @author seven sins
 * 2018年7月12日 下午7:55:10
 */
@SuppressWarnings("resource")
@RestController
public class ShopController {
	
	private static final String TOKEN = "c9fda8a042ab34db8cd735b395ee31f3";

	@GetMapping("/shop/{shopId}")
	public Object shop(@PathVariable("shopId") Long shopId) {
		YZClient client = new DefaultYZClient(new Token(TOKEN)); //new Sign(appKey, appSecret)
		YouzanItemGetParams youzanItemGetParams = new YouzanItemGetParams();
		youzanItemGetParams.setItemId(shopId); // 424449804L
		YouzanItemGet youzanItemGet = new YouzanItemGet();
		youzanItemGet.setAPIParams(youzanItemGetParams);
		YouzanItemGetResult result = client.invoke(youzanItemGet);
		System.out.println(result);
		return result;
	}
	
	@GetMapping("/user/create")
	public Object createUser() {
		YZClient client = new DefaultYZClient(new Token(TOKEN)); //new Sign(appKey, appSecret)
		YouzanScrmCustomerCreateParams youzanScrmCustomerCreateParams = new YouzanScrmCustomerCreateParams();
		youzanScrmCustomerCreateParams.setMobile("18816789926");
		youzanScrmCustomerCreateParams.setCustomerCreate("{\"name\":\"JJBB\",\"gender\":1,\"birthday\":\"2017-01-01\",\"remark\":\"OK\",\"contact_address\":{\"area_code\":210224,\"address\":\"猴子\"}}");
		YouzanScrmCustomerCreate youzanScrmCustomerCreate = new YouzanScrmCustomerCreate();
		youzanScrmCustomerCreate.setAPIParams(youzanScrmCustomerCreateParams);
		YouzanScrmCustomerCreateResult result = client.invoke(youzanScrmCustomerCreate);
		
		return result;
	}
	
	@GetMapping("/add/point")
	public Object addPoint() {
		YZClient client = new DefaultYZClient(new Token(TOKEN)); //new Sign(appKey, appSecret)
		YouzanCrmCustomerPointsIncreaseParams youzanCrmCustomerPointsIncreaseParams = new YouzanCrmCustomerPointsIncreaseParams();
		youzanCrmCustomerPointsIncreaseParams.setPoints(300L);
		youzanCrmCustomerPointsIncreaseParams.setMobile("18816789926");
		YouzanCrmCustomerPointsIncrease youzanCrmCustomerPointsIncrease = new YouzanCrmCustomerPointsIncrease();
		youzanCrmCustomerPointsIncrease.setAPIParams(youzanCrmCustomerPointsIncreaseParams);
		YouzanCrmCustomerPointsIncreaseResult result = client.invoke(youzanCrmCustomerPointsIncrease);
		
		return result;
	}
	
}
