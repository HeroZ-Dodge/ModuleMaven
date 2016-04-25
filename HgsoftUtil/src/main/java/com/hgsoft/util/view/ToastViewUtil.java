package com.hgsoft.util.view;

import com.hgsoft.util.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ToastViewUtil {
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
	
	private static Toast makeShowToast(Context context,String msg,int drawable,int time){
		try {
			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.toast_center, null);
			LinearLayout llLayout=(LinearLayout) view.findViewById(R.id.ll_toast);
			LayoutParams layoutParams=llLayout.getLayoutParams();
			int width = context.getResources().getDisplayMetrics().widthPixels;
			layoutParams.width=(int) ((width/10)*9);
			llLayout.setLayoutParams(layoutParams);
			ImageView imageView = (ImageView) view.findViewById(R.id.iv_toast);
			TextView textView = (TextView) view.findViewById(R.id.tv_toast);
			if (drawable>0) {
				imageView.setBackgroundResource(drawable);
			}
			textView.setText(msg+"");
			Toast toast = new Toast(context);
			toast.setGravity(Gravity.TOP,0,0);
			toast.setDuration(time);
			toast.setView(view);
			return toast;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * TODO(这里用一句话描述这个方法的作用)
	 * @Title:showToast
	 * 
	 * @author yudapei
	 */
	public static void showShortToast(Context context, String text) {
		// TODO Auto-generated method stub
		toastRunnable.setContext(context);
		toastRunnable.setText(text);
		toastRunnable.setDura(Toast.LENGTH_SHORT);
		if (context!=null&&context instanceof Activity) {
			((Activity) context).runOnUiThread(toastRunnable);
		}
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
		if (context!=null&&context instanceof Activity) {
			((Activity) context).runOnUiThread(toastRunnable);
		}
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
		private int drawable;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Toast toast=null;
			if (context!=null) {
				if (dura>0) {
					toast = makeShowToast(context, text, drawable, dura);
				}else {
					toast=makeShowToast(context, text, drawable, dura);
				}
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

		public int getDrawable() {
			return drawable;
		}

		public void setDrawable(int drawable) {
			this.drawable = drawable;
		}
	}
}
