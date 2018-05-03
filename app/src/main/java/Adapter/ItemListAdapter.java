package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mokuryuu.login.R;

import java.util.LinkedList;

import Models.ItemObject;

public class ItemListAdapter extends BaseAdapter {

    private LinkedList<ItemObject> datasource;
    private Context context;

    public ItemListAdapter(Context context,LinkedList<ItemObject> datasource){
        this.datasource = datasource;
        this.context = context;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return datasource.get(position);
    }

    @Override
    public int getCount() {
        return datasource.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.view_home_item, parent, false);
        TextView titleText = (TextView)convertView.findViewById(R.id.title_text);
        TextView contentText = (TextView)convertView.findViewById(R.id.content_text);
        ItemObject object = this.datasource.get(position);
        titleText.setText(object.getTitle());
        contentText.setText(object.getContent());
        return convertView;
    }
}
