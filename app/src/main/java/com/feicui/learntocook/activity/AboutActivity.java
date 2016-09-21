package com.feicui.learntocook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.learntocook.R;
import com.feicui.learntocook.fragment.Fragment_All;
import com.feicui.learntocook.fragment.Fragment_Lately;
import com.feicui.learntocook.fragment.Fragment_Like;

/**
 * Created by lenovo on 2016/9/7.
 * Fragment与ViewPager连用需要FragmentPagerAdapter(要直接使用getSupportFragmentManager()，该类必须继承 AppCompatActivity )
 */
public class AboutActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private Button btn_all,btn_lately,btn_like;
    private TextView textView_actionBar;
    private ImageView back_actionBar;//返回键图标
    private String menu="";
    private String title="";
    private int cid=0;
    private Fragment_All fragment_all=new Fragment_All();
    private Fragment_Lately fragment_lately=new Fragment_Lately();
    private Fragment_Like fragment_like=new Fragment_Like();
    //参数列表需要一个Fragment管理器
    private FragmentPagerAdapter adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
           switch (position){
               case 0:
                   setActionBar();
                   return fragment_all;
               case 1:
                    textView_actionBar.setText("最近浏览");
                   return fragment_lately;
               case 2:
                   textView_actionBar.setText("我的收藏");
                   return fragment_like;
                default:
                    throw new RuntimeException("未知错误");
           }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textView_actionBar= (TextView) findViewById(R.id.text_actionBar);
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        btn_all= (Button) findViewById(R.id.btn_all);
        btn_lately= (Button) findViewById(R.id.btn_lately);
        btn_like= (Button) findViewById(R.id.btn_like);
        back_actionBar= (ImageView) findViewById(R.id.back_actionBar);
        //获取菜名
        if (getIntent().getStringExtra("menu")!=null){
            menu=getIntent().getStringExtra("menu");
        }
        cid=getIntent().getIntExtra("cid",cid);
        Log.d("debug","传递的cid是"+cid);
        Bundle bundle=new Bundle();
        bundle.putString("menu",menu);
        bundle.putInt("cid",cid);//将获取到的cid传给fragment
        fragment_all.setArguments(bundle);

        viewPager.setAdapter(adapter);//给ViewPAger设置上适配器才能正常显示
        viewPager.addOnPageChangeListener(this);//ViewPAger设置监听
        btn_all.setSelected(true);//默认显示全部的这个页面
        setActionBar();
        back_actionBar.setOnClickListener(this);
        btn_all.setOnClickListener(this);
        btn_lately.setOnClickListener(this);
        btn_like.setOnClickListener(this);

    }

    /**页面滑动时的监听*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    /**页面选择时的监听，这里设置选择按钮UI做相应改变*/
    @Override
    public void onPageSelected(int position) {
        //当选择Fragment时，同步设置下方按钮的选择状态
        switch (position){
            case 0:
                setActionBar();
                btn_all.setSelected(true);
                btn_lately.setSelected(false);
                btn_like.setSelected(false);
                break;
            case 1:
                textView_actionBar.setText("最近浏览");
                btn_all.setSelected(false);
                btn_lately.setSelected(true);
                btn_like.setSelected(false);
                break;
            case 2:
                textView_actionBar.setText("我的收藏");
                btn_all.setSelected(false);
                btn_lately.setSelected(false);
                btn_like.setSelected(true);
                break;
        }
//                btn_all.setSelected(position==0);
//                btn_lately.setSelected(position==1);
//                btn_like.setSelected(position==2);



    }
    /**页面滑动状态变化时的监听*/
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        //点击button时，切换相应角标的Fragment，参数一角标，参数二是切换效果
        switch (view.getId()){
            case  R.id.btn_all:
                setActionBar();
                viewPager.setCurrentItem(0,true);

            break;
            case  R.id.btn_lately:
                textView_actionBar.setText("最近浏览");
                viewPager.setCurrentItem(1,true);

                break;
            case  R.id.btn_like:
                textView_actionBar.setText("我的收藏");
                viewPager.setCurrentItem(2,true);

                break;
            case R.id.back_actionBar:
                finish();
        }
    }
    private void setActionBar(){


        //获取标题
        if (getIntent().getStringExtra("title")!=null){
            title=getIntent().getStringExtra("title");
        }
        textView_actionBar.setText(title);
    }
}
