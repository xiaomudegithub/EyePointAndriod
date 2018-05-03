package Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.mokuryuu.login.LoginMainActivity;
import com.example.mokuryuu.login.R;

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
import butterknife.Unbinder;


public class FragmentHomeRootFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static Context mContext;
    private LinkedList<ItemObject> items = new LinkedList<>() ;
    private ItemListAdapter itemAdapter;

    @BindView(R.id.list_animal)
    ListView listAnimal;
    Unbinder unbinder;

    public static Fragment shareInstance(Context context) {
        FragmentHomeRootFragment fragment = new FragmentHomeRootFragment();
        mContext = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_root, null);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listAnimal.setOnItemClickListener(this);
        if (!SPTool.contains(mContext, "userId")){
            Intent intent = new Intent(mContext, LoginMainActivity.class);
            startActivity(intent);
            return;
        }
        getAllItmes();
    }


    private void getAllItmes() {
        HashMap param = new HashMap();
        param.put("userId", SPTool.get(mContext, "userId", 0));
        String url = "http://120.24.48.233:5011/Items/unfinishedItems/";
        BaseNetAsyncTask task = new BaseNetAsyncTask(mContext, BaseNetAsyncTask.NetType.POST, url, param, new BaseNetAsyncOnTaskListen() {
            @Override
            public void onSuccess(Context context, Object result) {
                List<ItemObject> objects = JSON.parseArray(result.toString(), ItemObject.class);
                itemAdapter = new ItemListAdapter(context, new LinkedList<ItemObject>(objects));
                listAnimal.setAdapter(itemAdapter);
            }

            @Override
            public void onFailure(Context context, Object msg) {
                Toast.makeText(context, msg.toString(), (int) 2).show();
                return;
            }
        });
        task.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
