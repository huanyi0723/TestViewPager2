package com.example.testviewpager;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.LinearLayout.LayoutParams;

public class ViewPagerAdapter extends PagerAdapter {

  private Context context;
  private ArrayList<String> fileNames = new ArrayList<String>(); // 本地图片路径
  
  private SeekBar seekBar;
  private ViewPager view_pager;

  public ViewPagerAdapter(Context context, ArrayList<String> fileNames, ViewPager view_pager ) {
    super();
    this.context = context;
    this.fileNames = fileNames;
    this.view_pager = view_pager;
  }

  @Override
  public int getCount() {
    return fileNames.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public View instantiateItem(ViewGroup container, int position) {

    View view = LayoutInflater.from(context).inflate(R.layout.pager_adapter_item, null);
    ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
    seekBar = (SeekBar) view.findViewById(R.id.seekBar);
    seekBar.setMax(100);
    
    seekBar.setTag("seekBar" + position);
    
    Log.i("mbk", "---------------ViewPagerAdapter -----------setTag--------------" + position);

    Glide.with(context).load(fileNames.get(position)).centerCrop().into(imageView);
    
    container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    return view;

  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  // 解决数据不刷新的问题
  @Override
  public int getItemPosition(Object object) {
    return POSITION_NONE;
  }

  public void setProgress(int progress, int currentPosition) {
    seekBar =  (SeekBar) view_pager.findViewWithTag("seekBar"+currentPosition);
    seekBar.setProgress(progress);
    
    Log.i("mbk", "---------------ViewPagerAdapter -----------progress--------------" + progress);
  }
}
