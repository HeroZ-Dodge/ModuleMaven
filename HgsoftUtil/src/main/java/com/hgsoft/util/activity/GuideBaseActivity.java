package com.hgsoft.util.activity;


import java.util.ArrayList;
import java.util.List;

import com.hgsoft.util.ImageHelper;
import com.hgsoft.util.R;
import com.hgsoft.util.adapter.ViewPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


public abstract class GuideBaseActivity extends FragmentActivity{
	
	protected int activity_layout=0;
	protected ViewPager viewPager;
	protected Button btn_center;
	protected LinearLayout ll_dots;
	protected ArrayList<Integer> pages;
	protected List<Fragment> fragments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActivityLayout();
		if (activity_layout!=0) {
			setContentView(activity_layout);
		}else {
			setContentView(R.layout.activity_base_guide);
		}
		initView();
	}
	
	private void initView(){
		viewPager=(ViewPager) findViewById(R.id.vp);
		btn_center=(Button) findViewById(R.id.btn_center);
		ll_dots=(LinearLayout) findViewById(R.id.ll_dots);
		btn_center.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doEnterNow();
			}
		});
		pages=new ArrayList<Integer>();
		initPages();
		initGuidePage();
	}
	private void initGuidePage(){
		fragments=new ArrayList<Fragment>();
		ImageView imageView;
		for (int i = 0; i < pages.size(); i++) {
			fragments.add(new GuideFragment(pages.get(i)));
			imageView=new ImageView(this);
			LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(20,20);
			layoutParams.leftMargin=10;
			imageView.setLayoutParams(layoutParams);
			ll_dots.addView(imageView);
		}
		ViewPagerAdapter pagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(0);
		updateDotBg(0);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if ((position+1)<=fragments.size()) {
					if ((position+1)==fragments.size()) {
						btn_center.setVisibility(View.VISIBLE);
					}else {
						btn_center.setVisibility(View.GONE);
					}
					updateDotBg(position);
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	private void updateDotBg(int position){
		for (int i = 0; i < fragments.size(); i++) {
			if (i==position) {
				ll_dots.getChildAt(i).setBackgroundResource(R.drawable.radio_adpoint_selected);
			}else {
				ll_dots.getChildAt(i).setBackgroundResource(R.drawable.radio_adpoint);
			}
		}
	}
	
	private class GuideFragment extends Fragment{
		private int drawable;
		public GuideFragment(int drawable){
			this.drawable=drawable;
		}
		@Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
			LinearLayout view=new LinearLayout(getApplicationContext()); 
			ImageView imageView=new ImageView(getApplicationContext());
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setImageBitmap(ImageHelper.getDrawable(getApplicationContext(), this.drawable));
			view.addView(imageView, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			return view;
	    }
	}

	/**
	 * 设置加载的导航页布局
	 * 必须包含vp(ViewPager),btn_enter(Button)和ll_dots(LinearLayout)
	 * id必须统一
	 * @Title:initActivityLayout 
	 * @author weiliu
	 */
	public  abstract void initActivityLayout();
	public  abstract void initPages();
	public  abstract void doEnterNow();
}

