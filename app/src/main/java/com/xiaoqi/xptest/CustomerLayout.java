package com.xiaoqi.xptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/8/8.
 */

public class CustomerLayout extends FrameLayout {
	Paint paint;
	int height;
	int width;

	final int innerImageWidth = dip2px(205);
	final int innerImageHeight = dip2px(135);
	public CustomerLayout(Context context) {
		super(context);
		init();
	}

	public CustomerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init(){
		setWillNotDraw(false);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor("#0099CC"));
		paint.setStrokeWidth(dip2px(8));
		paint.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Path path = new Path();
		path.lineTo(width, 0);
		path.lineTo(width, height - innerImageHeight);
		path.lineTo(width - innerImageWidth, height - innerImageHeight);
		path.lineTo(width - innerImageWidth, height);
		path.lineTo(0, height);
		path.lineTo(0, 0);
		canvas.drawPath(path, paint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
	}

	private int dip2px(float dipValue){
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}

}
