package com.example.dairy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.dairy.R;
import com.example.dairy.entity.DiaryEntity;

import java.util.List;


public class DiaryAdapter extends BaseAdapter {
    private Context context;
    private List<DiaryEntity> list;

    public DiaryAdapter(Context context, List<DiaryEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<DiaryEntity> list){
        this.list=list;
    }

    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_diary,null);

        TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
        TextView tv_date= (TextView) view.findViewById(R.id.tv_date);
        TextView tv_weather= (TextView) view.findViewById(R.id.tv_weather);
        TextView tv_des= (TextView) view.findViewById(R.id.tv_des);
        tv_title.setText(list.get(position).getTitle());
        tv_date.setText(list.get(position).getDate());
        tv_weather.setText(list.get(position).getWeather());
        tv_des.setText(list.get(position).getContent());
        return view;
    }
}
