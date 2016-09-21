package com.feicui.learntocook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.learntocook.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private TextView homeDishes_main,fastFood_main,iderFood_main,vegetable_main,
            coolFood_main,baking_main,noodle_main,soup_main,flavoring_main;
    private ImageView find_main;
    private EditText editText_main;
    private Button classification_main;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        find_main= (ImageView) findViewById(R.id.find_main);

        homeDishes_main= (TextView) findViewById(R.id.homeDishes_main);
        fastFood_main= (TextView) findViewById(R.id.fastFood_main);
        iderFood_main= (TextView) findViewById(R.id.iderFood_main);
        vegetable_main= (TextView) findViewById(R.id.Vegetable_main);
        coolFood_main= (TextView) findViewById(R.id.coolFood_main);
        baking_main= (TextView) findViewById(R.id.baking_main);
        noodle_main= (TextView) findViewById(R.id.noodle_main);
        soup_main= (TextView) findViewById(R.id.soup_main);
        flavoring_main= (TextView) findViewById(R.id.flavoring_main);
        classification_main= (Button) findViewById(R.id.classification_main);


        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        editText_main= (EditText) findViewById(R.id.ediText_main);

        editText_main.addTextChangedListener(this);
        find_main.setOnClickListener(this);
        homeDishes_main.setOnClickListener(this);


    }
    int cid=0;//传递分类的id
    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        switch (view.getId()){
            case R.id.find_main:
                intent.setClass(MainActivity.this,AboutActivity.class);
                intent.putExtra("title",inStr);
                intent.putExtra("menu",inStr);
                bundle.putInt("cid",0);//联网之前判断一下，如果为0就使用搜索的接口
                break;
            case R.id.homeDishes_main:
                intent.setClass(MainActivity.this,AboutActivity.class);
                intent.putExtra("title",homeDishes_main.getText().toString());
                bundle.putInt("cid",1);
                break;
            case R.id.fastFood_main:
                intent.setClass(MainActivity.this,AboutActivity.class);
                intent.putExtra("title",fastFood_main.getText().toString());
                bundle.putInt("cid",2);
                break;
            case R.id.iderFood_main:
                intent.setClass(MainActivity.this,AboutActivity.class);
                intent.putExtra("title",iderFood_main.getText().toString());
                bundle.putInt("cid",3);
                break;
            case R.id.Vegetable_main:
                intent.setClass(MainActivity.this,AboutActivity.class);
                intent.putExtra("title",vegetable_main.getText().toString());
                bundle.putInt("cid",4);
                break;
            case R.id.coolFood_main:
                intent.setClass(MainActivity.this,AboutActivity.class);
                intent.putExtra("title",coolFood_main.getText().toString());
                bundle.putInt("cid",5);
                break;
            case R.id.baking_main:
                intent.setClass(MainActivity.this,AboutActivity.class);
                intent.putExtra("title",baking_main.getText().toString());
                bundle.putInt("cid",6);
                break;
            case R.id.noodle_main:
                intent.setClass(MainActivity.this,AboutActivity.class);
                intent.putExtra("title",noodle_main.getText().toString());
                bundle.putInt("cid",7);
                break;
            case R.id.soup_main:
                intent.setClass(MainActivity.this,AboutActivity.class);
                intent.putExtra("title",soup_main.getText().toString());
                bundle.putInt("cid",8);
                break;
            case R.id.flavoring_main:
                intent.setClass(MainActivity.this,AboutActivity.class);
                intent.putExtra("title",flavoring_main.getText().toString());
                bundle.putInt("cid",9);
                break;
            case R.id.classification_main:
                intent.setClass(MainActivity.this,CookClassifyActivity.class);
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }
    String inStr;
    //Editext字符发生变化前的监听
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }
    //Editext字符发生变化时的监听
    @Override
    public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
        inStr=editText_main.getText().toString();
        if (inStr.length()>2){
            find_main.setVisibility(View.VISIBLE);
        }
        if (inStr.length()<2){
            find_main.setVisibility(View.INVISIBLE);
        }
    }
    //Editext字符发生变化后的监听
    @Override
    public void afterTextChanged(Editable editable) {

    }
}
