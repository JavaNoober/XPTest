package com.xiaoqi.xptest;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by xiaoqi on 2017/8/7.
 */

public class RotateImageViewAdapter2 extends BaseRotateAdapter {

	private ArrayList<ViewGroup> parentViewList;

	//用于交换图片的View
	private RotateImageView rotateImageView;

	@Override
	public boolean needChangePosition(RotateImageView imageView, float x, float y){
		if(imageView.getTop() == 0){
			Rect rect = getViewRect((ViewGroup)imageView.getParent());
			int right = rect.right;
			int bottom = rect.bottom;
			for(ViewGroup view : parentViewList){
				if(view != imageView.getParent()){
					Point point = new Point();
					point.x = right - dip2px(imageView.getContext(), 5) - view.getChildAt(0).getMeasuredWidth();
					point.y = bottom - dip2px(imageView.getContext(), 5) - view.getChildAt(0).getMeasuredHeight();
					if(x <= right && y <= bottom && x >= point.x && y >= point.y){
						rotateImageView = (RotateImageView) view.getChildAt(0);
						return true;
					}
				}
			}
		}else {
			for(ViewGroup view : parentViewList){
				if(view != imageView.getParent()){
					Rect rect = getViewRect(view);
					int right = rect.right;
					int bottom = rect.bottom;
					Point point = new Point();
					point.x = right - dip2px(imageView.getContext(), 5) - imageView.getMeasuredWidth();
					point.y = bottom - dip2px(imageView.getContext(), 5) - imageView.getMeasuredHeight();
					if(x> 0 && y > 0 && x <= point.x && y <= bottom){
						rotateImageView = (RotateImageView) view.getChildAt(0);
						return true;
					}else if(x> 0 && y > 0 && x >= point.x && y <= point.y){
						rotateImageView = (RotateImageView) view.getChildAt(0);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void changePosition(RotateImageView imageView){
		recoverEffect(imageView);
		if(rotateImageView != null){
			Drawable b = rotateImageView.getDrawable();
			rotateImageView.setImageDrawable(imageView.getDrawable());
			imageView.setImageDrawable(b);
		}
	}

	@Override
	public View inflateLayout(JigsawView jigsawView) {
		return LayoutInflater.from(jigsawView.getContext()).inflate(R.layout.layout_jigsaw2, jigsawView);
	}

	@Override
	public View getChangedView() {
		return rotateImageView;
	}

	@Override
	public void inflateView(View parentView, ArrayList<RotateImageView> rotateArrayList,
	                        ArrayList<ViewGroup> parentArrayList){
		RotateImageView riv1 = (RotateImageView) parentView.findViewById(R.id.riv1);
		RotateImageView riv2 = (RotateImageView) parentView.findViewById(R.id.riv2);
		RelativeLayout fl1 = (RelativeLayout) parentView.findViewById(R.id.rl_parent1);
		FrameLayout fl2 = (FrameLayout) parentView.findViewById(R.id.rl_parent2);
		rotateArrayList.add(riv1);
		rotateArrayList.add(riv2);
		parentArrayList.add(fl1);
		parentArrayList.add(fl2);
		parentViewList = parentArrayList;
	}


	private int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}
}
