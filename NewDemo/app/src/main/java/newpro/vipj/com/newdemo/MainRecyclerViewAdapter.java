package newpro.vipj.com.newdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by alee on 2015/7/4.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private ArrayList<Object> mylist;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }


    public MainRecyclerViewAdapter(Context context, ArrayList<Object> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mylist = items;
    }

    public void setDatas(ArrayList<Object> acticleMods) {
        if (acticleMods == null) {
            acticleMods = new ArrayList<Object>();
        } else {
            this.mylist = acticleMods;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
}
