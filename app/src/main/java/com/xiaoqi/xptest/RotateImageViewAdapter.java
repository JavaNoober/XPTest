package com.xiaoqi.xptest;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by xiaoqi on 2017/8/7.
 */

public class RotateImageViewAdapter extends BaseRotateAdapter {

	private ArrayList<ViewGroup> parentViewList;

	//用于交换图片的View
	private RotateImageView rotateImageView;

	@Override
	public boolean needChangePosition(RotateImageView imageView, float x, float y){
		for(ViewGroup view : parentViewList){
			Rect rect = getViewRect(view);
			if(view != imageView.getParent() && rect.contains((int)x, (int)y)){
				rotateImageView = (RotateImageView) view.getChildAt(0);
				return true;
			}
		}
		return false;
	}



	@Override
	public void changePosition(RotateImageView imageView){
		recoverEffect(imageView);
		Drawable b = rotateImageView.getDrawable();
		rotateImageView.setImageDrawable(imageView.getDrawable());
		imageView.setImageDrawable(b);
	}

	@Override
	public View inflateLayout(JigsawView jigsawView) {
		return LayoutInflater.from(jigsawView.getContext()).inflate(R.layout.layout_jigsaw1, jigsawView);
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
		FrameLayout fl1 = (FrameLayout) parentView.findViewById(R.id.fl_parent1);
		FrameLayout fl2 = (FrameLayout) parentView.findViewById(R.id.fl_parent2);
		rotateArrayList.add(riv1);
		rotateArrayList.add(riv2);
		parentArrayList.add(fl1);
		parentArrayList.add(fl2);
		parentViewList = parentArrayList;
	}

}
