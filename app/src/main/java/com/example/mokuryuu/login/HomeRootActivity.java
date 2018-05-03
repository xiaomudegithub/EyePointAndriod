package com.example.mokuryuu.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Adapter.ItemListAdapter;
import Models.ItemObject;
import NetAsyncTask.BaseNetAsyncOnTaskListen;
import NetAsyncTask.BaseNetAsyncTask;
import Tool.SPTool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeRootActivity extends Activity implements AdapterView.OnItemClickListener{

    private LinkedList<ItemObject> items = new LinkedList<>() ;
    private ItemListAdapter itemAdapter;

    @BindView(R.id.list_animal)
    ListView listAnimal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        ButterKnife.bind(this);
        listAnimal.setOnItemClickListener(this);
        if (!SPTool.contains(this, "userId")){
            Intent intent = new Intent(this, LoginMainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        getAllItmes();
    }

    private  void  getAllItmes(){
        HashMap param = new HashMap();
        param.put("userId", SPTool.get(this,"userId", 0));
        String url = "http://120.24.48.233:5011/Items/unfinishedItems/";
        BaseNetAsyncTask task = new BaseNetAsyncTask(this, BaseNetAsyncTask.NetType.POST, url, param, new BaseNetAsyncOnTaskListen() {
            @Override
            public void onSuccess(Context context, Object result) {
                List<ItemObject> objects = JSON.parseArray(result.toString(), ItemObject.class);
                itemAdapter = new ItemListAdapter(context, new LinkedList<ItemObject>(objects));
                 listAnimal.setAdapter(itemAdapter);
            }

            @Override
            public void onFailure(Context context, Object msg) {
                Toast.makeText(context, msg.toString(), (int)2).show();
                return;
            }
        });
        task.execute();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
