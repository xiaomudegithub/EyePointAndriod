package com.example.mokuryuu.login;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import Fragments.FragmentHomeRootFragment;
import Fragments.FragmentMessageRootFragment;
import Fragments.FragmentUserRootFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityTabMainActivity extends Activity {

    private Fragment[] framgments = new Fragment[]{
            FragmentHomeRootFragment.shareInstance(this),
            FragmentMessageRootFragment.shareInstance("message"),
            FragmentUserRootFragment.shareInstance("user")
    };
    private int[] selectIcon = new int[]{
            R.mipmap.items_tab,
            R.mipmap.message_tab,
            R.mipmap.user_tab
    };
    private int[] unselectIcon = new int[]{
            R.mipmap.items_untab,
            R.mipmap.message_untab,
            R.mipmap.user_untab
    };
    private String[] tabTitles = new String[]{
            "事项",
            "消息",
            "个人中心"
    };

    @BindView(R.id.content_fragments_layout)
    RelativeLayout contentFragmentsLayout;
    @BindView(R.id.content_layout)
    RelativeLayout contentLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view == null){
                    return;
                }
                ImageView icon = (ImageView) view.findViewById(R.id.icon_image);
                icon.setImageResource(selectIcon[tab.getPosition()]);
                TextView text = (TextView) view.findViewById(R.id.title_text);
                text.setTextColor(getResources().getColor(R.color.tab_select));
                if (framgments[tab.getPosition()].isAdded()){
                    getFragmentManager().beginTransaction().show(framgments[tab.getPosition()]).commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view == null){
                    return;
                }
                ImageView icon = (ImageView) view.findViewById(R.id.icon_image);
                icon.setImageResource(unselectIcon[tab.getPosition()]);
                TextView text = (TextView) view.findViewById(R.id.title_text);
                text.setTextColor(getResources().getColor(R.color.tab_unselect));
                if (framgments[tab.getPosition()].isAdded()){
                    getFragmentManager().beginTransaction().hide(framgments[tab.getPosition()]).commit();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0;i< tabTitles.length; i++){
            tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(this,i)));
            getFragmentManager().beginTransaction().add(R.id.content_fragments_layout, framgments[i]).commit();
            getFragmentManager().beginTransaction().hide(framgments[i]).commit();
        }



    }



    private View getTabView(Context context, int postion){
        View view = LayoutInflater.from(context).inflate(R.layout.view_tab_item, null);
        ImageView icon = (ImageView) view.findViewById(R.id.icon_image);
        icon.setImageResource(unselectIcon[postion]);
        TextView text = (TextView) view.findViewById(R.id.title_text);
        text.setText(tabTitles[postion]);
        return  view;
    }
}
