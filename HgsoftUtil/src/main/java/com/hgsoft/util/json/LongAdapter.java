package com.hgsoft.util.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * @ClassName: DoubleAdapter
 * @Description: TODO(用一句话描述该类做什么)
 * @author yudapei
 * @date 2014年10月17日 上午9:59:17
 */	
public class LongAdapter implements JsonDeserializer<Long> {

	@Override
	public Long deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		// TODO Auto-generated method stub
		if (arg0.getAsString().equals("null")) {
			return null;
		}
		try {
			return Long.valueOf(arg0.getAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}