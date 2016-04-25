package com.hgsoft.util.view;


import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hgsoft.util.R;
import com.hgsoft.util.ScreenUtil;

public class DialogWhiteUtil {

	private  static Dialog createCustomDialog(Context context, View view) {
		Dialog dialog;
		dialog = new Dialog(context, R.style.loading_dialog);
		int width = context.getResources().getDisplayMetrics().widthPixels;
		ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(width - (int) ScreenUtil.dip2px(context, 40), ViewGroup.LayoutParams.WRAP_CONTENT);
		dialog.setContentView(view, params);
		return dialog;
	}
	
	public static Dialog createDialogWhiteNote(Context context,String title,String note,String nText,String cText,String pText,View.OnClickListener nClickListener,View.OnClickListener cClickListener,View.OnClickListener pClickListener,boolean cancleFlag){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_white_note, null);
		final Dialog dialog =createCustomDialog(context, view);
		LinearLayout llLayout=(LinearLayout) view.findViewById(R.id.ll_dialog);
		LayoutParams layoutParams=llLayout.getLayoutParams();
		int width = context.getResources().getDisplayMetrics().widthPixels;
		int widthP=(int) ((width/7)*5);
		layoutParams.width=widthP;
		llLayout.setLayoutParams(layoutParams);
		TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
		TextView tvNote= (TextView) view.findViewById(R.id.tv_note);
		Button btnCancle = (Button) view.findViewById(R.id.btn_cancle);
		Button btnOk= (Button) view.findViewById(R.id.btn_ok);
		View viewSplit=  view.findViewById(R.id.view_split);
		Button btnCenter= (Button) view.findViewById(R.id.btn_center);
		View view_split_center=  view.findViewById(R.id.view_split_center);
		btnCenter.setVisibility(View.GONE);
		view_split_center.setVisibility(View.GONE);
		tvNote.setMovementMethod(ScrollingMovementMethod.getInstance());
		if (title!=null) {
			tvTitle.setText(title+"");
		}
		if (note!=null) {
			tvNote.setText(note+"");
		}
		if (nText!=null&&!nText.equals("")) {
			btnCancle.setText(nText+"");
		}else{
			btnCancle.setVisibility(View.GONE);
			viewSplit.setVisibility(View.GONE);
			btnOk.setBackgroundResource(R.drawable.dialog_white_note_btn_left_right_click);
		}
		if (pText!=null&&!pText.equals("")) {
			btnOk.setText(pText+"");
		}else{
			btnOk.setVisibility(View.GONE);
			viewSplit.setVisibility(View.GONE);
			btnCancle.setBackgroundResource(R.drawable.dialog_white_note_btn_left_right_click);
		}
		if (nText!=null&&!nText.equals("")&&pText!=null&&!pText.equals("")) {
			if (cText!=null&&!cText.equals("")) {
				btnCenter.setText(cText+"");
				btnCenter.setVisibility(View.VISIBLE);
				view_split_center.setVisibility(View.VISIBLE);
			}else{
				btnCenter.setVisibility(View.GONE);
				view_split_center.setVisibility(View.GONE);
				btnCenter.setBackgroundResource(R.drawable.dialog_white_note_btn_click);
			}
		}
		if (nClickListener!=null) {
			btnCancle.setOnClickListener(nClickListener);
		}else {
			btnCancle.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});
		}
		if (pClickListener!=null) {
			btnOk.setOnClickListener(pClickListener);
		}else {
			btnOk.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});
		}
		if (cClickListener!=null) {
			btnCenter.setOnClickListener(cClickListener);
		}else {
			btnCenter.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});
		}
		if (!cancleFlag) {
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
		}
		return dialog;
	}
	
	public static Dialog createDialogWhiteNote(Context context,String title,String note,String nText,String pText,View.OnClickListener nClickListener,View.OnClickListener pClickListener,boolean cancleFlag){
		return createDialogWhiteNote(context, title, note, nText, "", pText, nClickListener, null, pClickListener, cancleFlag);
	}
	
	public static Dialog createDialogWhiteNote(Context context,String title,String note,String nText,String pText,View.OnClickListener nClickListener,View.OnClickListener pClickListener){
		return createDialogWhiteNote(context, title, note, nText, pText, nClickListener, pClickListener,false);
	}
	
	public static Dialog createDialogCancleAndPositive(Context context,String title,String note,String pText,View.OnClickListener pClickListener){
		return createDialogWhiteNote(context, title, note, "取消", pText, null, pClickListener);
	}
	
	public static Dialog createDialogCancleAndPositive(Context context,String title,String note,View.OnClickListener pClickListener){
		return createDialogWhiteNote(context, title, note, "取消", "确定", null, pClickListener);
	}
	
	public static Dialog createDialogCancleAndPositive(Context context,String note,View.OnClickListener pClickListener){
		return createDialogWhiteNote(context, "提示", note, "取消", "确定", null, pClickListener);
	}
	
	public static Dialog createDialogPositive(Context context,String title,String note,String pText,View.OnClickListener pClickListener){
		return createDialogWhiteNote(context, title, note, null, pText, null, pClickListener);
	}
	
	public static Dialog createUncancleDialogPositive(Context context,String title,String note,String pText,View.OnClickListener pClickListener){
		Dialog dialog= createDialogWhiteNote(context, title, note, null, pText, null, pClickListener);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}
	
	public static Dialog createDialogPositive(Context context,String title,String note,View.OnClickListener pClickListener){
		return createDialogWhiteNote(context, title, note, null, "确定", null, pClickListener);
	}
	
	public static Dialog createDialogPositive(Context context,String note,View.OnClickListener pClickListener){
		return createDialogWhiteNote(context, "提示", note, null, "确定", null, pClickListener);
	}
	
	public static Dialog createDialogPositive(Context context,String title,String note){
		return createDialogWhiteNote(context,  title, note, null, "确定", null,null);
	}
}
