package com.xiaoqi.xptest;

import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/8.
 */

public abstract class BaseRotateAdapter {

	public abstract void inflateView(View parentView, ArrayList<RotateImageView> rotateArrayList,
	                        ArrayList<ViewGroup> parentArrayList);

	public abstract boolean needChangePosition(RotateImageView imageView, float x, float y);

	public abstract void changePosition(RotateImageView imageView);

	public abstract View inflateLayout(JigsawView jigsawView);

	public abstract View getChangedView();

	public void defaultEffect(RotateImageView currentView){
		currentView.setAlpha(0.5f);
		if(getChangedView() != null && ((ViewGroup)getChangedView().getParent()).getChildCount() > 1){
			((ViewGroup)getChangedView().getParent()).getChildAt(1).setVisibility(View.VISIBLE);
		}
	}

	public void recoverEffect(RotateImageView currentView){
		currentView.setAlpha(1f);
		if(getChangedView() != null && ((ViewGroup)getChangedView().getParent()).getChildCount() > 1){
			((ViewGroup)getChangedView().getParent()).getChildAt(1).setVisibility(View.GONE);
		}
	}

	Rect getViewRect(View view){
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1];
		return new Rect(x, y, x + view.getMeasuredWidth(), y + view.getMeasuredHeight());
	}
}
