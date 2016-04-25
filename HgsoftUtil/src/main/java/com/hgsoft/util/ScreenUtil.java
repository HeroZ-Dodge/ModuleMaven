package com.hgsoft.util;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 * 1、dip px 转换 2、自适应屏幕 3、获取屏幕 原始宽高 4、获取屏幕 pix宽高
 * 
 */
public class ScreenUtil {

	/**
	 * 根据分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static float dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return dpValue * scale;
	}

	/**
	 * 根据分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static float px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return pxValue / scale;
	}

	/**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale
     * DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    } 
     
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    }
	/**
	 * 根据屏幕大小自动放大缩小窗口各控件及字体
	 * 
	 * @param a
	 * @param original_x_dp
	 *            原始窗口dip宽度
	 * @param original_y_dp
	 *            原始窗口dip高度
	 */
	public static void autoSize(Activity a, float original_x_dp, float original_y_dp) {
		float original_x_px = dip2px(a, original_x_dp);
		float original_y_px = dip2px(a, original_y_dp);

		// DisplayMetrics dm = new DisplayMetrics();
		// a.getWindowManager().getDefaultDisplay().getMetrics(dm);
		// float xr=1.0f*dm.widthPixels/original_x_px;
		// float yr=1.0f*dm.heightPixels/original_y_px;

		float xr = 1.0f * getScreenWidth(a) / original_x_px;
		float yr = 1.0f * getScreenHeight(a) / original_y_px;

		autoSize(a.getWindow().getDecorView(), xr, yr);
	}

	private static void autoSize(View view, float xr, float yr) {
		float minr = Math.min(xr, yr);

		if (view instanceof TextView) {
			reSize(view, xr, yr);
			((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, ((TextView) view).getTextSize() * minr - 1.0f);
		} else if (view instanceof EditText) {
			reSize(view, xr, yr);
			((EditText) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, ((EditText) view).getTextSize() * minr - 1.0f);
		} else if (view instanceof Button) {
			reSize(view, xr, yr);
			((Button) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, ((Button) view).getTextSize() * minr - 1.0f);
		} else if (view instanceof RadioButton) {
			reSize(view, xr, yr);
			((RadioButton) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, ((RadioButton) view).getTextSize() * minr - 1.0f);
		} else if (view instanceof Spinner) {
			reSize(view, xr, yr);
		} else if (view instanceof GridView) {
			reSize(view, xr, yr);
		} else if (view instanceof ImageView) {
			reSize(view, xr, yr);
		} else if (view instanceof LinearLayout) {
			reSize(view, xr, yr);
		} else {
			reSize(view, xr, yr);
		}

		if (view instanceof ViewGroup) {
			ViewGroup vp = (ViewGroup) view;
			for (int i = 0; i < vp.getChildCount(); i++) {
				View viewchild = vp.getChildAt(i);
				autoSize(viewchild, xr, yr);
			}
		}
	}

	private static void reSize(View view, float xr, float yr) {
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (lp != null) {
			if (lp.width > 0)
				lp.width = Math.round(lp.width * xr);
			if (lp.height > 0)
				lp.height = Math.round(lp.height * yr);
		}

		if (lp instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams lp2 = (ViewGroup.MarginLayoutParams) lp;
			if (lp2.leftMargin > 0)
				lp2.leftMargin = Math.round(lp2.leftMargin * xr);
			if (lp2.topMargin > 0)
				lp2.topMargin = Math.round(lp2.topMargin * yr);
			if (lp2.rightMargin > 0)
				lp2.rightMargin = Math.round(lp2.rightMargin * xr);
			if (lp2.bottomMargin > 0)
				lp2.bottomMargin = Math.round(lp2.bottomMargin * yr);
		}
	}

	/**
	 * 获得屏幕原始pix宽度
	 * 
	 * @param a
	 * @return 屏幕原始pix宽度
	 */
	public static int getRawScreenWidth(Activity a) {
		DisplayMetrics dm = new DisplayMetrics();
		android.view.Display display = a.getWindowManager().getDefaultDisplay();
		display.getMetrics(dm);

		int ver = Build.VERSION.SDK_INT;
		if (ver == 13) {
			try {
				Method mt = display.getClass().getMethod("getRealWidth");
				return (Integer) mt.invoke(display);
			} catch (Exception e) {
				return dm.widthPixels;
			}
		} else if (ver > 13) {
			try {
				Method mt = display.getClass().getMethod("getRawWidth");
				return (Integer) mt.invoke(display);

			} catch (Exception e) {
				return dm.widthPixels;
			}
		} else
			return dm.widthPixels;
	}

	/**
	 * 获得屏幕显示pix宽度
	 * 
	 * @param a
	 * @return 屏幕显示pix宽度
	 */
	public static int getScreenWidth(Activity a) {
		DisplayMetrics dm = new DisplayMetrics();
		android.view.Display display = a.getWindowManager().getDefaultDisplay();
		display.getMetrics(dm);
		return dm.widthPixels;
		// return getRawScreenWidth(a);
	}

	/**
	 * 获得屏幕原始pix高度
	 * 
	 * @param a
	 * @return 屏幕原始pix高度
	 */
	public static int getRawScreenHeight(Activity a) {
		DisplayMetrics dm = new DisplayMetrics();
		android.view.Display display = a.getWindowManager().getDefaultDisplay();
		display.getMetrics(dm);

		int ver = Build.VERSION.SDK_INT;
		if (ver == 13) {
			try {
				Method mt = display.getClass().getMethod("getRealHeight");
				return (Integer) mt.invoke(display);
			} catch (Exception e) {
				return dm.heightPixels;
			}
		} else if (ver > 13) {
			try {
				Method mt = display.getClass().getMethod("getRawHeight");
				return (Integer) mt.invoke(display);

			} catch (Exception e) {
				return dm.heightPixels;
			}
		} else
			return dm.heightPixels;
	}

	/**
	 * 获得屏幕显示pix高度
	 * 
	 * @param a
	 * @return 屏幕显示pix高度
	 */
	public static int getScreenHeight(Activity a) {
		DisplayMetrics dm = new DisplayMetrics();
		android.view.Display display = a.getWindowManager().getDefaultDisplay();
		display.getMetrics(dm);
		return dm.heightPixels;
	}

	
	public static void autoSizeViewHeight(Activity a,View view,float wDpValue,double w,double h){
		double width=getScreenWidth(a)-dip2px(a, wDpValue);
		double viewWidth=width*w;
		double viewHeight=width*h;
		LayoutParams para=view.getLayoutParams();
		para.width=(int) viewWidth;
		para.height=(int) viewHeight;
		view.setLayoutParams(para);
	}
	
	public static void installShortCut(Activity activity,String appName,int ic_launcher){
		try{
			Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
	        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
	        // 是否可以有多个快捷方式的副本，参数如果是true就可以生成多个快捷方式，如果是false就不会重复添加
	        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, generatorContactCountIcon(activity,((BitmapDrawable)(activity.getResources().getDrawable(ic_launcher))).getBitmap()));
	        shortcutIntent.putExtra("duplicate", false);
	        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
	        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	        // 要删除的应用程序的ComponentName，即应用程序包名+activity的名字
	        mainIntent.setClass(activity, activity.getClass());
	        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, mainIntent);
	        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, generatorContactCountIcon(activity,((BitmapDrawable)(activity.getResources().getDrawable(ic_launcher))).getBitmap()));
	        activity.sendBroadcast(shortcutIntent);
		}catch(Exception e){
		}
    }
     
    private  static Bitmap generatorContactCountIcon(Activity activity,Bitmap icon){
    	if(activity!=null){
    		 //初始化画布
            int iconSize=(int)activity.getResources().getDimension(android.R.dimen.app_icon_size);
            Bitmap contactIcon=Bitmap.createBitmap(iconSize, iconSize, Config.ARGB_8888);
            Canvas canvas=new Canvas(contactIcon);
            //拷贝图片
            Paint iconPaint=new Paint();
            iconPaint.setDither(true);//防抖动
            iconPaint.setFilterBitmap(true);//用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
            Rect src=new Rect(0, 0, icon.getWidth(), icon.getHeight());
            Rect dst=new Rect(0, 0, iconSize, iconSize);
            canvas.drawBitmap(icon, src, dst, iconPaint);
            //在图片上创建一个覆盖的联系人个数
            int contacyCount=11;
            //启用抗锯齿和使用设备的文本字距
            Paint countPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DEV_KERN_TEXT_FLAG);
            countPaint.setColor(Color.RED);
            countPaint.setTextSize(20f);
            countPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(String.valueOf(contacyCount), iconSize-18, 25, countPaint);
            return contactIcon;
    	}
    	 return null;
    }

}
