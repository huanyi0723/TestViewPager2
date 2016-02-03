package com.example.testviewpager;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
  
  private ViewPager view_pager; //预览照片的分页显示
  private ViewPagerAdapter viewPagerAdapter; //分页图片的预览器
  
  private ArrayList<String> fileNames = new ArrayList<String>(); //本地图片路径
  private int currentPosition = 0; //当前选择的图片
  
  private Timer timer = new Timer(); // 定时器
  private TimerTask timerTask;
  private int progress = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    initData();
    initView();
  }

  // 获取本地所有照片路径
  private void initData() {
    
    fileNames.clear();
    Cursor cursor = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, null, null, null, null);
    while (cursor.moveToNext()) {
      byte[] data = cursor.getBlob(cursor.getColumnIndex(Media.DATA)); //图片的保存位置的数据
      fileNames.add(new String(data, 0, data.length - 1));
    }
    
  }

  //初始化控件
  private void initView() {
    view_pager = (ViewPager) findViewById(R.id.view_pager);
    viewPagerAdapter = new ViewPagerAdapter(this, fileNames, view_pager, currentPosition);
    view_pager.setAdapter(viewPagerAdapter);
    
    
    view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      
      @Override
      public void onPageSelected(int i) {
        currentPosition = i;
        
        Log.i("mbk", "翻页后的当前页面" + currentPosition);
      }
      
      @Override
      public void onPageScrolled(int i, float f, int j) {
      }
      
      @Override
      public void onPageScrollStateChanged(int i) {
      }
    });
    
    Button delete = (Button)findViewById(R.id.delete);
    delete.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        if (fileNames.size() > 0) {
          fileNames.remove(currentPosition);
        }
        Log.i("mbk", "删除的当前页面" + currentPosition);
        viewPagerAdapter.notifyDataSetChanged();
      }
    });
    
    Button update = (Button)findViewById(R.id.update);
    update.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        initTimeTask();
      }
    });
    
  }
  
  
  // 定时器轮询
  private void initTimeTask() {

    timerTask = new TimerTask() {
      @Override
      public void run() {
        viewPagerAdapter.setProgress( progress , currentPosition);
        
        Log.i("mbk", "---------------MainActivity -----------progress--------------" + progress);
        progress++;
      }
    };

    timer.schedule(timerTask, 0, 1000);

  }
  
}
