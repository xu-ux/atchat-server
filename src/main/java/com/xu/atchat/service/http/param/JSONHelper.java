package com.xu.atchat.service.http.param;

import com.alibaba.fastjson.JSONObject;
import com.xu.atchat.service.http.exception.HttpException;
/**
 * @description JSON 处理辅助类
 */
public class JSONHelper {

	private JSONHelper() {
	}

	/**
	 * 转换响应数据
	 *
	 * @param json
	 * @param clz
	 * @param <T>
	 * @return
	 * @throws HttpException
	 */
	public static <T> T castDataJson(JSONObject json, Class<T> clz) throws RuntimeException {
		Object obj = json.get("data");
		int code = json.getIntValue("code");
		if (code != 0 && obj == null) {
			throw new HttpException.OperationFailure(json.getString("message"));
		}
		return (T)obj;
	}

}
