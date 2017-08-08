package com.xiaoqi.xptest;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by xiaoqi on 2017/8/7.
 */

public class JigsawView extends FrameLayout{

	private ArrayList<RotateImageView> rotateImageViewArrayList = new ArrayList<>();
	private ArrayList<ViewGroup> viewGroupArrayList = new ArrayList<>();
	private ArrayList<BaseRotateAdapter> adapterArrayList = new ArrayList<>();


	private OnItemClickListener onItemClickListener;

	public JigsawView(@NonNull Context context) {
		super(context);
	}

	public JigsawView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public JigsawView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		this.onItemClickListener = onItemClickListener;
	}

	public void addAdapter(BaseRotateAdapter adapter){
		adapterArrayList.add(adapter);
	}

	public void setMould(int mould){
		BaseRotateAdapter adapter = adapterArrayList.get(mould);
		if(adapter != null){
			if(getChildCount() > 0){
				removeAllViews();
			}
			rotateImageViewArrayList.clear();
			viewGroupArrayList.clear();
			adapter.inflateView(adapter.inflateLayout(this), rotateImageViewArrayList, viewGroupArrayList);
			for(RotateImageView rotateImageView : rotateImageViewArrayList){
				rotateImageView.setAdapter(adapter);
				if(onItemClickListener != null){
					rotateImageView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							onItemClickListener.onItemClick(v);
						}
					});
				}
			}
			invalidate();
		}
	}


}
