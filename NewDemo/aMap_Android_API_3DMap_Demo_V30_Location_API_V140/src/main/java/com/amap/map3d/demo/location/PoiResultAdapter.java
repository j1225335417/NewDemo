package com.amap.map3d.demo.location;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.map3d.demo.R;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by zhaoyl on 2015/5/29.
 */
public class PoiResultAdapter extends BaseAdapter {
    private List<PoiItem> data;
    private ViewHolder holder;

    private Context context;
    private boolean bool = false;

    public PoiResultAdapter(List<PoiItem> list, Context context, boolean bool) {
        if(list==null){
            this.data=new ArrayList<PoiItem>();
        }else{
            this.data = list;
        }
        this.context = context;
        this.bool=bool;
    }
    public  void refresh(List<PoiItem> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {

        return data.size();
    }
    public List<PoiItem> getData(){
        return data;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.poi_search_result, null);
            holder.name = (TextView) convertView.findViewById(R.id.item_name);
            holder.address = (TextView) convertView.findViewById(R.id.item_address);
            holder.icon = (ImageView) convertView.findViewById(R.id.item_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PoiItem info = data.get(position);
        if (bool && position == 0) {
            holder.icon.setImageResource(R.drawable.location_icon);
            holder.name.setTextColor(Color.parseColor("#9dce63"));
            holder.name.setText(info.getSnippet());
            holder.address.setText(info.getTitle());
        } else {
            holder.icon.setImageResource(R.drawable.location_other_icon);
            //holder.address.setTextColor(Color.parseColor("#c9c9c9"));
            holder.name.setTextColor(Color.BLACK);
            holder.name.setText(info.getSnippet());
            holder.address.setText(info.getTitle());
        }
        return convertView;
    }



    static class ViewHolder {
        TextView name;
        TextView address;
        ImageView icon;
    }



}
