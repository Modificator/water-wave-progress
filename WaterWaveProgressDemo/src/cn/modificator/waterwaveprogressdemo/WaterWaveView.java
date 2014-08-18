package cn.modificator.waterwaveprogressdemo;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class WaterWaveView extends View {
	/** 水的画笔 */
	private Paint mPaintWater = null;
	/** 产生波浪效果的因子 */
	private long mWaveFactor = 0L;
	/** 正在执行波浪动画 */
	private boolean isWaving = false;
	/** 振幅 */
	private float mAmplitude = 30.0F; // 20F
	/** 波浪的速度 */
	private float mWaveSpeed = 0.080F; // 0.020F
	/** 水的高度占容器总高度的百分比 */
	private float mWaterLevel = 0.5F;
	/** 水的透明度 */
	private int mWaterAlpha = 255; // 255
	/** 水的颜色 */
	private int mWaterColor = 0x4bbdfe;

	private MyHandler mHandler = null;

	private static class MyHandler extends Handler {
		private WeakReference<WaterWaveView> mWeakRef = null;

		private int refreshPeriod = 100;

		public MyHandler(WaterWaveView host) {
			mWeakRef = new WeakReference<WaterWaveView>(host);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (mWeakRef.get() != null) {
				mWeakRef.get().invalidate();
				sendEmptyMessageDelayed(0, refreshPeriod);
			}
		}
	}

	public WaterWaveView(Context paramContext) {
		super(paramContext);
		init(paramContext);
	}

	public WaterWaveView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context);
	}

	@SuppressLint("NewApi")
	private void init(Context paramContext) {
		// setBackgroundColor(0x804bbdfe);
		//setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		mPaintWater = new Paint();
		mPaintWater.setStrokeWidth(1.0F);
		// mPaintWater.setColor(mWaterColor);
		mPaintWater.setColor(mWaterColor);
		mPaintWater.setAlpha(mWaterAlpha);
		mHandler = new MyHandler(this);
	}

	public void animateWave() {
		if (!isWaving) {
			mWaveFactor = 0L;
			isWaving = true;
			mHandler.sendEmptyMessage(0);
		}
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取整个View（容器）的宽、高
		int width = getWidth()-100;
		int height = getHeight()-100;
		//width = height = (width < height) ? width : height;
		mAmplitude = width/20f;


		// 如果没有执行波浪动画，或者也没有指定容器宽高，就画个简单的矩形
//		if (!isWaving || (width == 0) || (height == 0)) {
//			canvas.drawRect(0.0F, height / 2, width, height, mPaintWater);
//			return;
//		}

		// 重新生成波浪的形状
		mWaveFactor++;
		if (mWaveFactor >= Integer.MAX_VALUE) {
			mWaveFactor = 0L;
		}


		// 计算出水的高度
		float waterHeight = (float) (height * mWaterLevel);
		// 先把水静止的部分画出来
		int staticHeight = (int) (waterHeight + mAmplitude);
		
		Paint bgPaint = new Paint();
		bgPaint.setColor(0xff000000);
		//canvas.drawRect(0f, 0f, width, height, bgPaint);
		//canvas.drawRect(0.0F, staticHeight, width, height, mPaintWater);

		// waterHeight*=0.8f;
		// 待绘制的波浪线的x坐标
		int xToBeDrawed = 50;
		// 波浪线高度
		int waveHeight = (int) (waterHeight - mAmplitude
				* Math.sin(Math.PI
						* (2.0F * (xToBeDrawed + (mWaveFactor * width)
								* mWaveSpeed)) / width));
		// 波浪线新的高度
		int newWaveHeight = waveHeight;
		while (true) {
			if (xToBeDrawed >= width+50) {
				break;
			}
			// 根据当前x值计算波浪线新的高度
			newWaveHeight = (int) (waterHeight - mAmplitude
					* Math.sin(Math.PI
							* (2.0F * (xToBeDrawed + (mWaveFactor * width)
									* mWaveSpeed)) / width));
			// 先画出梯形的顶边
			 //canvas.drawLine(xToBeDrawed, waveHeight, xToBeDrawed + 1,
			// newWaveHeight, mPaintWater);
			Log.e("------", xToBeDrawed + "  newWaveHeight:" + newWaveHeight+" staticHeight:"+staticHeight+" waveHeight:"+waveHeight);
			// 画出动态变化的柱子部分
			canvas.drawLine(xToBeDrawed, newWaveHeight, xToBeDrawed,
					staticHeight, mPaintWater);
			xToBeDrawed++;
			waveHeight = newWaveHeight;
		}
	}

	/**
	 * 设置波浪的振幅
	 */
	public void setAmplitude(float amplitude) {
		mAmplitude = amplitude;
	}

	/**
	 * 设置水的透明度
	 * 
	 * @param alpha
	 *            透明的百分比，值为0到1之间的小数，越接近0越透明
	 */
	public void setWaterAlpha(float alpha) {
		mWaterAlpha = (int) (255.0F * alpha);
		mPaintWater.setAlpha(mWaterAlpha);
	}

	/** 设置水的颜色 */
	public void setWaterColor(int color) {
		mWaterColor = color;
	}

	/**
	 * 设置水在容器里的百分比
	 */
	public void setWaterLevel(float level) {
		mWaterLevel = level;
		invalidate();
	}
	public float getWaterLevel() {
		return mWaterLevel;
	}
	
	/**
	 * 设置波浪速度
	 */
	public void setWaveSpeed(float speed) {
		mWaveSpeed = speed;
	}
}