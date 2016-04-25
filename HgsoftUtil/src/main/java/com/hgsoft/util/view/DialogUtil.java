/**
 * @Project Name:BaseUtilLibrary 
 * @Title: DialogUtil.java
 * @Package com.hgsoft.baseutillibrary
 * @Description: TODO((用一句话描述该文件做什么)
 * @author yudapei
 * @date 2014年10月8日 下午3:38:20
 */
package com.hgsoft.util.view;



import com.hgsoft.util.R;
import com.hgsoft.util.ScreenUtil;
import com.hgsoft.util.R.id;
import com.hgsoft.util.R.layout;
import com.hgsoft.util.R.style;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * @ClassName: DialogUtil
 * @Description: TODO(用一句话描述该类做什么)
 * @author yudapei
 * @date 2014年10月8日 下午3:38:20
 */
public class DialogUtil {
	public static DialogUtil showDialog;

	public static DialogUtil instance() {// 实例化
		if (showDialog == null) {
			showDialog = new DialogUtil();
		}
		return showDialog;
	}

	/**
	 * loading的对话框
	 * 
	 * @Title:loadingDialog
	 * @param context
	 * @param message
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Dialog createLoadingDialog(Context context, String message) {
		Dialog dialog;
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_base_loading, null);
		TextView tvMessage = (TextView) linearLayout.findViewById(R.id.tv_message);
		if (tvMessage != null) {
			tvMessage.setText(message);
		}
		dialog = new Dialog(context, R.style.loading_dialog);
		dialog.setContentView(linearLayout);
		return dialog;
	}
	
	/**
	 * loading的对话框
	 * 
	 * @Title:loadingDialog
	 * @param context
	 * @param message
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Dialog createWriteCardLoadingDialog(Context context, String message) {
		Dialog dialog;
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_write_card_loading, null);
		TextView tvMessage = (TextView) linearLayout.findViewById(R.id.tv_message);
		if (tvMessage != null) {
			tvMessage.setText(message);
		}
		dialog = new Dialog(context, R.style.loading_dialog);
		dialog.setContentView(linearLayout);
		return dialog;
	}

	/**
	 * 自定义的对话框
	 * 
	 * @Title:customDialog
	 * @param context
	 * @param view
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Dialog createCustomDialog(Context context, View view) {
		Dialog dialog;
		dialog = new Dialog(context, R.style.loading_dialog);
		int width = context.getResources().getDisplayMetrics().widthPixels;
		ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(width - (int) ScreenUtil.dip2px(context, 40), ViewGroup.LayoutParams.WRAP_CONTENT);
		dialog.setContentView(view, params);
		return dialog;
	}

	/**
	 * 确认对话框
	 * 
	 * @Title:promptDialog
	 * @param context
	 * @param title
	 * @param message
	 * @param view
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Dialog createPromptDialog(Context context, String title, String message) {
		Dialog dialog;
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("确定", null);
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 确认对话框
	 * 
	 * @Title:createPromptDialog
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveOnclickListener
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Dialog createPromptDialog(Context context, String title, String message, OnClickListener positiveOnclickListener) {
		Dialog dialog;
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("确定", positiveOnclickListener);
		dialog = builder.create();
		return dialog;
	}
	
	/**
	 * 确认对话框
	 * 
	 * @Title:createPromptDialog
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveOnclickListener
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Dialog createPromptDialog(Context context, String title, String message,String ptxt,OnClickListener positiveOnclickListener) {
		Dialog dialog;
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(ptxt+"", positiveOnclickListener);
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 确认对话框
	 * 
	 * @Title:createPromptDialog
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveOnclickListener
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Dialog createPromptAndCancelDialog(Context context, String title, String message, String pBtn, String nBtn, OnClickListener positiveOnclickListener,
			OnClickListener nevigaClickListener) {
		Dialog dialog;
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title + "");
		builder.setMessage(message + "");
		builder.setPositiveButton(pBtn + "", positiveOnclickListener);
		if (nBtn != null && !nBtn.equals("") && nevigaClickListener != null) {
			builder.setNegativeButton(nBtn + "", nevigaClickListener);
		}
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 确认对话框(有取消按钮)
	 * 
	 * @Title:createPromptDialog
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveOnclickListener
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Dialog createPromptAndCancelDialog(Context context, String title, String message, OnClickListener positiveOnclickListener) {
		Dialog dialog;
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("确定", positiveOnclickListener);
		builder.setNegativeButton("取消", null);
		dialog = builder.create();
		return dialog;
	}
	
	/**
	 * 确认对话框(有取消按钮)
	 * 
	 * @Title:createPromptDialog
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveOnclickListener
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Dialog createPromptAndCancelDialog(Context context, String title, String message, String posTxt, String neuTxt,String negTxt, OnClickListener positiveOnclickListener,OnClickListener neutralOnclickListener,OnClickListener negativeOnclickListener) {
		Dialog dialog;
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(posTxt, positiveOnclickListener);
		builder.setNeutralButton(neuTxt, neutralOnclickListener);
		builder.setNegativeButton(negTxt, negativeOnclickListener);
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 自定义对话框，有标题，有确定取消按钮
	 * 
	 * @Title:createCustomDialog
	 * @param context
	 * @param title
	 * @param view
	 * @return
	 * 
	 * @author yudapei
	 */
	public static Dialog createCustomDialog(Context context, String title, View view, OnClickListener positiveOnclickListener, OnClickListener negativeOnclickListener) {
		Dialog dialog;
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title).setView(view);
		builder.setPositiveButton("确定", positiveOnclickListener);
		builder.setNegativeButton("取消", negativeOnclickListener);
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 显示对话框
	 * 
	 * @Title:showDialog
	 * @param dialog
	 * 
	 * @author yudapei
	 */
	public static void showDialog(Dialog dialog) {
		try {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			dialog.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解除对话框
	 * 
	 * @Title:dismissDialog
	 * @param dialog
	 * 
	 * @author yudapei
	 */
	public static void dismissDialog(Dialog dialog) {
		try {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在achor的下方弹出对话框
	 * 
	 * @param view
	 *            对话框的布局
	 * @param achor
	 */
	public static PopupWindow getPopUpWindow(View view) {
		PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new AnimationDrawable());
		return popupWindow;
	}

	public static PopupWindow getPopUpWindow(View view, int width, int height) {
		PopupWindow popupWindow = new PopupWindow(view, width, height);
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new AnimationDrawable());
		return popupWindow;
	}

	public static void showNfcDialog(final Context context) {
		createPromptAndCancelDialog(context, "提示", "本设备不支持nfc或者nfc功能没有启用,请检查!", "去打开NFC", "返回", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent =null;
				try {
					 intent = new Intent(Settings.ACTION_SETTINGS);
					 context.startActivity(intent);
				} catch (Exception e) {
				}
			}
		}, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (context instanceof Activity) {
					((Activity) context).finish();
				}
			}
		}).show();
	}
	
	
	/**
	 * 蓝牙设置窗口
	 * TODO
	 * @Title:showBluetoothDialog
	 * @param context 
	 *
	 * @author weiliu
	 */
	public static void showBluetoothDialog(final Context context) {
		createPromptAndCancelDialog(context, "提示", "本设备蓝牙功能没有启用,请检查!", "去打开蓝牙", "取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent =null;
				try {
					 intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
					 context.startActivity(intent);
				} catch (Exception e) {
				}
			}
		}, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
	}
	

}
