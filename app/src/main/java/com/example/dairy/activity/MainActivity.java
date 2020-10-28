package com.example.dairy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.dairy.R;
import com.example.dairy.adapter.DiaryAdapter;
import com.example.dairy.db.DbHelper;
import com.example.dairy.entity.DiaryEntity;
import com.example.dairy.util.DateUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView lv;
    private TextView tv_add;
    private List<DiaryEntity> list=new ArrayList<>();
    private DiaryAdapter adapter;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvOk;

    private int startLastMonth;
    private int startLastYear;
    private int startLastDay;

    private int endLastMonth;
    private int endLastYear;
    private int endLastDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData("","");
    }

    private void initData(String start,String end) {
        DbManager dbManager= DbHelper.getInstance().getDbManager(this,null);
        try {
            //得到数据库中的日记信息的集合
            list=dbManager.findAll(DiaryEntity.class);
            if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)){
                long b=DateUtil.changeToMillSecond(start);
                long c=DateUtil.changeToMillSecond(end);

                List<DiaryEntity> shaixuanList=new ArrayList<>();
                for (DiaryEntity item : list){
                    String date=item.getDate();
                    long a= DateUtil.changeToMillSecond(date);

                    if (a<=c && a>=b){
                        shaixuanList.add(item);
                    }
                }
                list=shaixuanList;
                adapter.setData(list);
                adapter.notifyDataSetChanged();
            }else {
                if (list!=null){
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                    lv.setAdapter(adapter);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    private void initEvent() {
        tv_add.setOnClickListener(this);
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setCancelable(true)
                        .setItems(new String[]{"编辑", "删除"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent=new Intent();
                                    intent.setClass(MainActivity.this,EditDiaryActivity.class);
                                    intent.putExtra("title",list.get(position).getTitle());
                                    intent.putExtra("des",list.get(position).getContent());
                                    intent.putExtra("date",list.get(position).getDate());
                                    intent.putExtra("weather",list.get(position).getWeather());
                                    intent.putExtra("id",list.get(position).getId());
                                    startActivity(intent);
                                } else {
                                    DbManager dbManager=DbHelper.getInstance().getDbManager(MainActivity.this,null);
                                    try {
                                        DiaryEntity diaryEntity =dbManager.findById(DiaryEntity.class,list.get(position).getId());
                                        dbManager.delete(diaryEntity);
                                        list.remove(position);
                                        adapter.setData(list);
                                        lv.setAdapter(adapter);
                                        Toast.makeText(MainActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }).show();
            }
        });
    }

    private void initView() {
        tvStartDate= (TextView) findViewById(R.id.tv_start_date);
        tvEndDate= (TextView) findViewById(R.id.tv_end_date);
        tvOk= (TextView) findViewById(R.id.tv_ok);
        lv= (ListView) findViewById(R.id.lv);
        tv_add= (TextView) findViewById(R.id.tv_add);
        adapter=new DiaryAdapter(this,list);
        lv.setAdapter(adapter);

        startLastYear= DateUtil.getCurrentYear();
        startLastMonth=DateUtil.getCurrentMonth();
        startLastDay=DateUtil.getCurrentDay();

        endLastYear=DateUtil.getCurrentYear();
        endLastMonth=DateUtil.getCurrentMonth();
        endLastDay=DateUtil.getCurrentDay();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add:
                Intent intent=new Intent();
                intent.setClass(this,AddDiaryActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_start_date:
                showStartPickDateDialog();
                break;
            case R.id.tv_end_date:
                showEndPickDateDialog();
                break;
            case R.id.tv_ok:
                String startDate=tvStartDate.getText().toString().trim();
                String endDate=tvEndDate.getText().toString().trim();
                if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)){
                    initData(startDate,endDate);
                }else {
                    Toast.makeText(this,"请选择开始日期和结束日期",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    private void showStartPickDateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker datePicker = new DatePicker(this);
        datePicker.setCalendarViewShown(false);
        datePicker.updateDate(startLastYear, startLastMonth - 1, startLastDay);
        builder.setView(datePicker).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int tempYear = datePicker.getYear();
                int tempMonth = datePicker.getMonth() + 1;
                int tempDay = datePicker.getDayOfMonth();
                String realMonth;
                String realDay;
                if (tempMonth<10){
                    realMonth="0"+tempMonth;
                }else {
                    realMonth=tempMonth+"";
                }
                if (tempDay<10){
                    realDay="0"+tempDay;
                }else {
                    realDay=tempDay+"";
                }

                String time = tempYear + "-" + realMonth + "-" + realDay;

                tvStartDate.setText(time);
                startLastYear = tempYear;
                startLastMonth = tempMonth;
                startLastDay = tempDay;

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }



    private void showEndPickDateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker datePicker = new DatePicker(this);
        datePicker.setCalendarViewShown(false);
        datePicker.updateDate(endLastYear, endLastMonth - 1, endLastDay);
        builder.setView(datePicker).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int tempYear = datePicker.getYear();
                int tempMonth = datePicker.getMonth() + 1;
                int tempDay = datePicker.getDayOfMonth();
                String realMonth;
                String realDay;
                if (tempMonth<10){
                    realMonth="0"+tempMonth;
                }else {
                    realMonth=tempMonth+"";
                }
                if (tempDay<10){
                    realDay="0"+tempDay;
                }else {
                    realDay=tempDay+"";
                }

                String time = tempYear + "-" + realMonth + "-" + realDay;

                tvEndDate.setText(time);
                endLastYear = tempYear;
                endLastMonth = tempMonth;
                endLastDay = tempDay;

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }
}
