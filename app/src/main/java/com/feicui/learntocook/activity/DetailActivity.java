package com.feicui.learntocook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.learntocook.R;
import com.feicui.learntocook.adapter.DetailAdapter;
import com.feicui.learntocook.db.CookDbUtil;
import com.feicui.learntocook.entity.CookInfo;
import com.feicui.learntocook.network.NetApi;
import com.feicui.learntocook.network.NetClient;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 2016/9/12.
 */
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
//    private final String APIKEY="1217ca9885abe430e9aae68d4c55d784";
//    private final String APIKEY="eee8cc2139db056de19bb5bce2c0d5cb";
    private final String APIKEY="09712326e450de60408f6415b4b705f6";

    private TextView cookName_detail,textViewLike_detail,imtro_detail,ingredients_detail,burden_detail,textView_actionBar;
    private ImageView titleImage_detail,back_actionBar,imageLike_detail,imageShare_detail;
    private LinearLayout linearLayout;
    private String cookId="";
    private String cookName="";
    private String tags="";
    private String imtro="";
    private String ingredients="";
    private String burden="";
    private String albums="";
    private RecyclerView recyclerView_detail;
    private DetailAdapter adapter;
    private int id;//Fragment传递的int型id
    private NetApi netApi;
    private List<CookInfo.ResultBean.DataBean.StepsBean> stepslist;//步骤列表
    private CookInfo.ResultBean.DataBean cookData;//菜谱数据的实体类
    private CookInfo.ResultBean.DataBean.StepsBean cookSteps;//菜谱步骤的实体类
    private List<CookInfo.ResultBean.DataBean> cookItemList,likeCookList;
    private boolean isLike=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        CookDb cookDb=new CookDb(this);
//        SQLiteDatabase db=null;
//        db=cookDb.getWritableDatabase();
//        cookDb.onUpgrade(db,1,2);
//        cookDb.close();
             initView();
        cookName=getIntent().getStringExtra("cookName");
        id=getIntent().getIntExtra("id",id);//接收Fragment传递的id
        Log.d("debug","从Fragment传递的ID是："+id);
        Log.d("debug","初始时islike的状态"+isLike);
        //判断数据库中是否有该菜谱，如果有就使用数据库数据加载，否则联网获取
        cookItemList=CookDbUtil.getsIntence(getBaseContext()).findCookData();
//        if (cookItemList!=null){
            for (int i=0;i<cookItemList.size();i++){
                if (cookItemList.get(i).getId().equals(id)){
                    setDetailData();
                    Log.d("debug","从数据库中获取数据填充");
                }

            }
//        }
            getNetData();//联网获取数据

        //判断是否在收藏列表中，如果在就更改收藏的状态更改UI
        likeCookList=CookDbUtil.getsIntence(getBaseContext()).findLikeCookData();
        if (likeCookList!=null){
            String likeId=id+"";
           for (int i=0;i<likeCookList.size();i++){
               if (likeId.equals(likeCookList.get(i).getId())){
                   textViewLike_detail.setText("已收藏");
                   imageLike_detail.setImageResource(R.mipmap.cang_red);
                   isLike=true;
                   Log.d("debug","数据库有收藏时islike的状态"+isLike);
               }else {
                   textViewLike_detail.setText("收藏");
                   imageLike_detail.setImageResource(R.mipmap.cang);
                   isLike=false;
                   Log.d("debug","数据库没有收藏时islike的状态"+isLike);
               }
           }
        }
        textView_actionBar.setText(cookName);//设置actionBar菜名
        cookName_detail.setText(cookName);


            //点击返回图标关闭当前页面
            back_actionBar.setOnClickListener(this);
            //点击调用系统分享
            imageShare_detail.setOnClickListener(this);
            //点击收藏与取消收藏
            imageLike_detail.setOnClickListener(this);


    }

    /**
     * 初始化视图
     */
    private void initView() {
        cookName_detail= (TextView) findViewById(R.id.cookName_detail);//菜名
        textViewLike_detail= (TextView) findViewById(R.id.textViewLike_detail);//收藏
        imageLike_detail= (ImageView) findViewById(R.id.imageLike_detail);//收藏的图片
        imtro_detail= (TextView) findViewById(R.id.imtro_detail);//菜谱的简介
        ingredients_detail= (TextView) findViewById(R.id.ingredients_detail);//菜谱的主料
        burden_detail= (TextView) findViewById(R.id.burden_detail);//菜谱的配料
        titleImage_detail= (ImageView) findViewById(R.id.titleImage_detail);//展示图片
        recyclerView_detail= (RecyclerView) findViewById(R.id.recyclerView_detail);//步骤
        back_actionBar= (ImageView) findViewById(R.id.back_actionBar);//actionBar返回图标
        textView_actionBar= (TextView) findViewById(R.id.text_actionBar);//actionBar标题栏
        linearLayout= (LinearLayout) findViewById(R.id.linearLayout);
        imageShare_detail= (ImageView) findViewById(R.id.imageShare_detail);
    }

    /**
     * 联网获取数据的方法
     */
    private void getNetData(){
        netApi= NetClient.getInstance(getBaseContext()).getNetApi();
        Call<CookInfo> call=netApi.getCookId(APIKEY,id);
        call.enqueue(callback);
    }

    /**
     * 联网获取数据的CallBack
     */
    private Callback<CookInfo> callback=new Callback<CookInfo>() {
        @Override
        public void onResponse(Call<CookInfo> call, Response<CookInfo> response) {
            if (!response.body().getResultcode().equals("200")){
                Toast.makeText(getBaseContext(), "数据异常", Toast.LENGTH_SHORT).show();
                return;
            }
            //获取相关字符串
            cookId=response.body().getResult().getData().get(0).getId();
            tags=response.body().getResult().getData().get(0).getTags();
            imtro=response.body().getResult().getData().get(0).getImtro();
            ingredients=response.body().getResult().getData().get(0).getIngredients();
            burden=response.body().getResult().getData().get(0).getBurden();
            albums=response.body().getResult().getData().get(0).getAlbums().get(0);

//            albumsList=response.body().getResult().getData().get(0).getAlbums();//预览图的集合

            //将步骤信息添加到集合
            stepslist=response.body().getResult().getData().get(0).getSteps();//步骤的集合
            Log.d("debug","setpsList集合的长度是："+stepslist.size());

            cookItemList=response.body().getResult().getData();

            //设置图片
            Picasso.with(getBaseContext()).load(albums).into(titleImage_detail);
            setDetailData();//获取到数据后设置UI的方法

            // 获取到摘要数据之后，将数据添加到数据库保存
            cookData=new CookInfo.ResultBean.DataBean();
            cookData.setId(cookItemList.get(0).getId());
            cookData.setTitle(cookItemList.get(0).getTitle());
            cookData.setTags(cookItemList.get(0).getTags());
            cookData.setImtro(cookItemList.get(0).getImtro());
            cookData.setIngredients(cookItemList.get(0).getIngredients());
            cookData.setBurden(cookItemList.get(0).getBurden());
            cookData.setAlbums(cookItemList.get(0).getAlbums());
            Log.d("debug","TAGS:"+cookData.getTags());
            CookDbUtil.getsIntence(getBaseContext()).addCookData(cookData);//调用添加的方法

            cookSteps=new CookInfo.ResultBean.DataBean.StepsBean();
            for (int i=0;i<stepslist.size();i++){
                cookSteps.setImg(stepslist.get(i).getImg());
                cookSteps.setStep(stepslist.get(i).getStep());
                Log.d("debug","即将存数据库的步骤信息是："+ cookSteps.getStep());

                CookDbUtil.getsIntence(getBaseContext()).addCookSteps(cookSteps,cookData);
            }


        }

        @Override
        public void onFailure(Call<CookInfo> call, Throwable t) {
            Toast.makeText(getBaseContext(),"数据异常"+t.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 获取到数据后设置UI的方法
     */
    private void setDetailData(){
        imtro_detail.setText(imtro);
        ingredients_detail.setText(ingredients);
        burden_detail.setText(burden);

        if (stepslist.size()<=0){
            Toast.makeText(getBaseContext(),"数据异常",Toast.LENGTH_SHORT).show();
            return;
        }
        adapter=new DetailAdapter(stepslist,getBaseContext());
        recyclerView_detail.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView_detail.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_actionBar:
                finish();
                break;
            case R.id.imageLike_detail:
                addLike();
                break;
            case R.id.imageShare_detail:
                Intent intent=new Intent(Intent.ACTION_SEND);//启动分享发送的属性
                intent.setType("text/plain");//分享发送的数据类型
                String msg="你很有想法，跟我学做菜吧";
                intent.putExtra(Intent.EXTRA_TEXT,msg);//分享的类型及内容
                startActivity(Intent.createChooser(intent,"选择分享"));// 目标应用选择对话框的标题
                break;


        }
    }
    private void addLike(){
        if (cookData==null){
            Toast.makeText(getBaseContext(),"请检查您的网络连接",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("debug","点击前isLike的状态"+isLike);
        if (isLike==false){
            CookDbUtil.getsIntence(getBaseContext()).addLikeCookData(cookData);//点击后添加到收藏列表
            for (int i=0;i<stepslist.size();i++){
                cookSteps.setImg(stepslist.get(i).getImg());
                cookSteps.setStep(stepslist.get(i).getStep());
                CookDbUtil.getsIntence(getBaseContext()).addLikeCookSteps(cookSteps,cookData);
            }
            textViewLike_detail.setText("已收藏");
            imageLike_detail.setImageResource(R.mipmap.cang_red);
            isLike=true;
            Log.d("debug","数据库没有收藏时点击islike的状态"+isLike);
            Toast.makeText(getBaseContext(),"收藏成功",Toast.LENGTH_SHORT).show();
        } else if (isLike==true){
            CookDbUtil.getsIntence(getBaseContext()).deleteLikeCook(cookData);
            textViewLike_detail.setText("收藏");
            imageLike_detail.setImageResource(R.mipmap.cang);
            isLike=false;
            Log.d("debug","数据库有收藏时点击islike的状态"+isLike);
            Toast.makeText(getBaseContext(),"已取消收藏",Toast.LENGTH_SHORT).show();
        }
    }
}
