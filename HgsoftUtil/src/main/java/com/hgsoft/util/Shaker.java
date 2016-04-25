package com.hgsoft.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;

/**
 * @author weiliu 摇动类
 * 
 */
public class Shaker {
	
	private Context context;

	/**
	 * 传感器管理
	 */
	private SensorManager mgr = null;

	/**
	 * 最后摇动的时间
	 */
	private long lastShakeTimestamp = 0;

	/**
	 * 偏移量设置
	 */
	private double threshold = 1.0d;

	/**
	 * 摇动的时间间隔设置
	 */
	private long gap = 0;

	/**
	 * 回调
	 */
	private Shaker.Callback cb = null;

	/**
	 * 变量控制，防止多次启动
	 */
	private boolean mPause = false;

	/**
	 * 传感器类
	 * 
	 * @param ctxt
	 *            上下文
	 * @param threshold
	 *            偏移量
	 * @param gap
	 *            传感器的时间
	 * @param cb
	 *            回调方法
	 */
	public Shaker(Context ctxt, double threshold, long gap, Shaker.Callback cb) {
		this.threshold = threshold * threshold;
		this.threshold = this.threshold * SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH;
		this.context = ctxt;
		this.gap = gap;
		this.cb = cb;
		// 注册传感器
		mgr = (SensorManager) ctxt.getSystemService(Context.SENSOR_SERVICE);
	}
	
	private void isShaking() {
		long now = SystemClock.uptimeMillis();

		if (lastShakeTimestamp == 0) {
			lastShakeTimestamp = now;

			if (cb != null) {
				cb.shakingStarted();
			}
		} else {
			lastShakeTimestamp = now;
		}
	}

	private void isNotShaking() {
		long now = SystemClock.uptimeMillis();

		if (lastShakeTimestamp > 0) {
			if (now - lastShakeTimestamp > gap) {
				lastShakeTimestamp = 0;

				if (cb != null) {
					cb.shakingStopped();
				}
			}
		}
	}

	public interface Callback {
		void shakingStarted();

		void shakingStopped();
	}

	private SensorEventListener listener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent e) {
			if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				double netForce = e.values[0] * e.values[0];

				netForce += e.values[1] * e.values[1];
				netForce += e.values[2] * e.values[2];
				// 偏移量,X^2 + Y^2 + Z^2 与最大的偏移量threshold相比，如果超过就作为在摇动
				if ((threshold < netForce) && !mPause) {
					VibratorUtils.Vibrate(context, 400);
					isShaking();
				} else {
					isNotShaking();
				}
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// unused
		}
	};

	public void open(){
		// 注册传感器的监听事件
		mgr.registerListener(listener, mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
	}

	public void close() {
		mgr.unregisterListener(listener);
	}
	
	public void resume() {
		mPause = false;
	}

	public void pause() {
		mPause = true;
	}
}