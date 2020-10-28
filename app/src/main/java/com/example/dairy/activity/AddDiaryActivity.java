package com.example.dairy.activity;

import android.app.Activity;
import android.os.Bundle;
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
import com.example.dairy.util.DateUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;


public class AddDiaryActivity extends Activity implements View.OnClickListener{

    private ImageView iv_back;
    private EditText et_title;
    private EditText et_content;
    private TextView tv_ok;
    private TextView tv_date;
    private String date;
    private Spinner sp_type;
    private String weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        initView();
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(this);
        tv_ok.setOnClickListener(this);

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
        et_title= (EditText) findViewById(R.id.et_title);
        et_content= (EditText) findViewById(R.id.et_content);
        tv_ok= (TextView) findViewById(R.id.tv_ok);
        tv_date= (TextView) findViewById(R.id.tv_date);
        sp_type= (Spinner) findViewById(R.id.sp_type);
        date= DateUtil.getYearMonthDay();
        tv_date.setText(date);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_ok:
                String title=et_title.getText().toString().trim();
                String content=et_content.getText().toString().trim();

            if (!title.isEmpty() && !content.isEmpty()){
                DbManager dbManager= DbHelper.getInstance().getDbManager(this,null);
                DiaryEntity entity=new DiaryEntity();
                entity.setDate(date);
                entity.setTitle(title);
                entity.setContent(content);
                entity.setWeather(weather);
                try {
                    dbManager.save(entity);
                    Toast.makeText(this,"添加成功！",Toast.LENGTH_SHORT).show();
                    finish();
                } catch (DbException e) {
                    e.printStackTrace();
                }

                }else {
                Toast.makeText(this,"请输入完整内容信息！",Toast.LENGTH_SHORT).show();
            }

                break;
        }
    }
}
