package com.hgsoft.util.json;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CommonUtil {

	/**
	 * json转map
	 * 
	 * @Title:contentToMap
	 * @param content
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Map<String, Object> contentToMap(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (content != null && content.startsWith("{")) {
				Gson gson = new Gson();
				map = gson.fromJson(content, new TypeToken<Map<String, Object>>() {
				}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * content转具体的类
	 * 
	 * @Title:contentToClass
	 * @param classes
	 * @return
	 * 
	 * @author yudapei
	 */
	public static <T> T contentToClass(String content, Class<?> classes) {
		Object object = null;
		try {
			GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Long.class, new LongAdapter()).registerTypeAdapter(Date.class, new DateAdapter()).registerTypeAdapter(Double.class, new DoubleAdapter());
			Gson gson = builder.create();
			object = gson.fromJson(content, classes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) object;
	}

	public static <T> List<T> contentToClassList(String jsonString, Type type) {
		List<T> list = new ArrayList<T>();
		try {
			GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Long.class, new LongAdapter()).registerTypeAdapter(Date.class, new DateAdapter()).registerTypeAdapter(Double.class, new DoubleAdapter());
			Gson gson = builder.create();
			list = gson.fromJson(jsonString, type);
		} catch (Exception e) {
		}
		return list;
	}

	/**
	 * content转任意类型
	 * 
	 * @Title:contentToAny
	 * @param s
	 * @param typeToken
	 * @return
	 * 
	 * @author yudapei
	 */
	public static <T> T contentToAny(String s, TypeToken<?> typeToken) {
		try {
			GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Long.class, new LongAdapter()).registerTypeAdapter(Date.class, new DateAdapter()).registerTypeAdapter(Double.class, new DoubleAdapter());
			Gson gson = builder.create();
			return (T) gson.fromJson(s, typeToken.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 处理字符串的格式（符合json）
	 * 
	 * @Title:orderObjectContent
	 * @param objectCotent
	 * @return
	 * 
	 * @author yudapei
	 */
	public static String orderObjectContent(String objectCotent) {
		String result = "";
		result = objectCotent.replace("{", "{\"").replace("}", "\"}").replace(", ", "\",\"").replace("=", "\":\"").replace("\"{", "{").replace("}\"", "}").replace("\"[", "[").replace("]\"", "]");
		return result;
	}


	public static void clearImageView(ImageView imageView) {
		if (!(imageView.getDrawable() instanceof BitmapDrawable))
			return;
		BitmapDrawable b = (BitmapDrawable) imageView.getDrawable();
		imageView.setImageBitmap(null);
		imageView.setImageResource(0);
		imageView.setBackgroundResource(0);
		if (b != null) {
			b.setCallback(null);
			Bitmap bitmap = b.getBitmap();
			if (bitmap != null) {
				bitmap.recycle();
				bitmap = null;
			}
		}
		System.gc();
	}

	public static BitmapDrawable getLocalImageDrawable(Activity activity, int resId) {
		BitmapDrawable bd = null;
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = activity.getResources().openRawResource(resId);
		Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
		bd = new BitmapDrawable(activity.getResources(), bm);
		bm = null;
		return bd;
	}
	
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	public static boolean isPassword(String password) {
		Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	public static boolean isVCode(String code) {
		Pattern p = Pattern.compile("^\\d{6}$");
		Matcher m = p.matcher(code);
		return m.matches();
	}

	/**
	 * 生成带星星的字符串(比如：1***5)
	 * @param idNum
	 * @return
	 */
	public static String getStarString(String idNum) {
		if (idNum == null) {
			return "";
		} else if (idNum.length() < 2) {
			return idNum;
		}
		String starArray = "*******************************";
		int starLength = starArray.length();
		while(idNum.length() > starLength) {
			starArray += starArray;
			starLength += starArray.length();
		}
		return idNum.substring(0, 1) + starArray.substring(0, idNum.length() - 2) + idNum.substring(idNum.length() - 1, idNum.length());
	}

}
