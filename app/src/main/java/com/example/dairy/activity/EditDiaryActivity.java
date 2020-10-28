package com.example.dairy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dairy.R;
import com.example.dairy.db.DbHelper;
import com.example.dairy.entity.DiaryEntity;


import org.xutils.DbManager;
import org.xutils.ex.DbException;


public class EditDiaryActivity extends Activity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_date;
    private EditText et_title;
    private EditText et_des;
    private Spinner sp_type;
    private TextView tv_ok;

    private String date;
    private String title;
    private String des;
    private String weather;
    private String[] weathers;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        title=getIntent().getStringExtra("title");
        date=getIntent().getStringExtra("date");
        des=getIntent().getStringExtra("des");
        weather=getIntent().getStringExtra("weather");
        id=getIntent().getIntExtra("id",0);


        Log.e("ZZ",id+"");

        initView();
        initEvent();

    }

    private void initEvent() {
        iv_back.setOnClickListener(this);
        tv_ok.setOnClickListener(this);

        //得到之前选中的天气在Spinner控件中的位置
        weathers=getResources().getStringArray(R.array.weather_type);
        int index=-1;
        for (int i=0;i<weathers.length;i++){
            if (weathers[i].equals(weather)){
                index=i;
                break;
            }
        }
        //将之前选中的天气显示在Spinner控件上
        sp_type.setSelection(index);
        ////得到Spinner控件中选中的天气
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weather= (String) sp_type.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void initView() {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        tv_date= (TextView) findViewById(R.id.tv_date);
        et_title= (EditText) findViewById(R.id.et_title);
        et_des= (EditText) findViewById(R.id.et_content);
        sp_type= (Spinner) findViewById(R.id.sp_type);
        tv_ok= (TextView) findViewById(R.id.tv_ok);

        tv_date.setText(date);
        et_title.setText(title);
        et_des.setText(des);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_ok:
                title=et_title.getText().toString().trim();
                des=et_des.getText().toString().trim();
                if (!title.isEmpty() && !des.isEmpty()){
                    //将修改后的日记的标题、内容、时间、天气保存到数据库中，是通过xutil3.0框架进行数据库操作的
                    DbManager dbManager= DbHelper.getInstance().getDbManager(this,null);

                    try {
                        DiaryEntity diaryEntity =dbManager.findById(DiaryEntity.class,id);
                        diaryEntity.setTitle(title);
                        diaryEntity.setWeather(weather);
                        diaryEntity.setDate(date);
                        diaryEntity.setContent(des);

                        dbManager.update(diaryEntity);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this,"编辑成功！",Toast.LENGTH_SHORT).show();
                    finish();

                }else {
                    Toast.makeText(this,"请将信息填完整！",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
