package com.hgsoft.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MScrollView extends ScrollView {

	private ScrollListener scrollListener;

	public MScrollView(Context context) {
		super(context);
	}

	public MScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MScrollView(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt){
		if(t + getHeight() >=  computeVerticalScrollRange()){
			//ScrollView滑动到底部了
			scrollListener.scrollBottom();
		}
		if (t>0) {
			scrollListener.scrollTop(t);
		}
	}

	public void setScrollListener(ScrollListener scrollListener){
		this.scrollListener = scrollListener;
	}

	public interface ScrollListener{
		public void scrollBottom();
		public void scrollTop(int y);
	}


}
