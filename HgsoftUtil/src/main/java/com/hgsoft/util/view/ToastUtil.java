/**
 * @Project Name:BaseUtilLibrary 
 * @Title: ToastUtil.java
 * @Package com.hgsoft.baseutillibrary
 * @Description: TODO((用一句话描述该文件做什么)
 * @author yudapei
 * @date 2014年10月10日 上午11:12:29
 */
package com.hgsoft.util.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: ToastUtil
 * @Description: TODO(用一句话描述该类做什么)
 * @author yudapei
 * @date 2014年10月10日 上午11:12:29
 */
public class ToastUtil {
	private static ToastRunnable toastRunnable = new ToastRunnable();
	/**
	 * 设置Toast的显示时间，Toast.LENGTH_SHORT或者Toast.LENGTH_LONG
	 */
	public static int duration = Toast.LENGTH_SHORT;
	/**
	 * 设置Toast风格
	 */
	public static int style;
	/**
	 * 系统风格
	 */
	public static int STYLE_SYSTEM = 0;
	/**
	 * 半透明风格
	 */
	public static int STYLE_CUSTOM_TRANSLUCENT = 1; 
	/**
	 * 系统风格（中间显示）
	 */
	public static int STYLE_SYSTEM_CENTER = 2; 
	/**
	 * 创建一个Toast
	 * @Title:createToast
	 * 
	 * @author yudapei
	 */
	private static Toast createToast(Context context, String text,int dura) {
		// TODO Auto-generated method stub
		Toast toast=null;
		if (context!=null) {
			if (dura>0) {
				 toast = Toast.makeText(context, text, dura);
			}else {
				 toast = Toast.makeText(context, text, duration);
			}
			 if (style == STYLE_CUSTOM_TRANSLUCENT) {
					TextView textView = new TextView(context);
					textView.setBackgroundColor(Color.parseColor("#44000000"));
					textView.setText(text);
					textView.setTextColor(Color.BLACK);
					textView.setPadding(20, 10, 20, 10);
					toast.setView(textView);
				} else if(style == STYLE_SYSTEM_CENTER) {
					toast.setGravity(Gravity.CENTER, 0, 0);
				}
		}
		return toast;
	}

	/**
	 * TODO(这里用一句话描述这个方法的作用)
	 * @Title:showToast
	 * 
	 * @author yudapei
	 */
	public static void showToast(Context context, String text) {
		// TODO Auto-generated method stub
		toastRunnable.setContext(context);
		toastRunnable.setText(text);
		((Activity) context).runOnUiThread(toastRunnable);
	}
	
	/**
	 * TODO(这里用一句话描述这个方法的作用)
	 * @Title:showToast
	 * 
	 * @author yudapei
	 */
	public static void showLongToast(Context context, String text) {
		// TODO Auto-generated method stub
		toastRunnable.setContext(context);
		toastRunnable.setText(text);
		toastRunnable.setDura(Toast.LENGTH_LONG);
		((Activity) context).runOnUiThread(toastRunnable);
	}

	/**
	 * 
	 * @ClassName: ToastRunnable
	 * @Description: 用于Toast的线程
	 * @author yudapei
	 * @date 2014年10月10日 下午7:13:38
	 */
	static class ToastRunnable implements Runnable {
		private Context context;
		private String text;
		private int dura;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Toast toast=null;
			if (dura>0) {
				toast = createToast(context, text,dura);
			}else {
				toast= createToast(context, text,0);
			}
			if (toast!=null) {
				toast.show();
			}
			context = null;
		}

		public Context getContext() {
			return context;
		}

		public void setContext(Context context) {
			this.context = context;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public int getDura() {
			return dura;
		}

		public void setDura(int dura) {
			this.dura = dura;
		}

	}
}
