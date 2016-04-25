package com.hgsoft.util.json;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * @ClassName: DateAdapter
 * @Description: TODO(用一句话描述该类做什么)
 * @author yudapei
 * @date 2014年10月16日 下午8:42:42
 */
public class DateAdapter implements JsonDeserializer<Date> {
	private final DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
	private final DecimalFormat decimalFormat=new DecimalFormat("#.#");

	@Override
	public Date deserialize(JsonElement json, Type arg1,
			JsonDeserializationContext context) throws JsonParseException {
		if (json.getAsString().equals("null")) {
			return null;
		}
		Date date = null;
		try {
			date = df.parse(json.getAsString().replace("T", " "));
		} catch (ParseException e) {
			try {
				long time=Long.parseLong(decimalFormat.format(json.getAsJsonPrimitive().getAsDouble()));
				date=new java.util.Date(time);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return date;
	}
	
}