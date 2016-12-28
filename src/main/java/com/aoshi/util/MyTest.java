package com.aoshi.util;

import java.util.Map;

import org.apache.log4j.Logger;

public class MyTest {

	private static Logger LOGGER = Logger.getLogger(MyTest.class);

	public static void main(String[] args) {

		HttpUtils client = HttpUtils.getInstance();
		String result = client.sendPostMethod(
						"http://localhost/redRain/shopowner/opera?action=1&account=shop_account&password=123456&tel=18520248756&logoUrl=http://www.baidu.com&name=shopName&descp=shopDescp&contact=shopContact",
						Conditions.newInstance(), "UTF-8");
		LOGGER.debug(result);

		// LOGGER.debug(Arrays.asList("2010031906272929^80.25^SUCCESS"
		// .split("\\^")));
	}

	private static Map<String, Object> buildParams() {

		Conditions conditions = CommonUtils.getConditions();
		conditions.putData("batch_no", "20160727133206")
				.putData("success_num", 1)
				.putData("result_details", "2010031906272929^80.25^SUCCESS");
		return conditions;
	}
}
