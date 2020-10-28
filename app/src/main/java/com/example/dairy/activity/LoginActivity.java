package com.example.dairy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dairy.R;
import com.example.dairy.util.SharedUtil;


public class LoginActivity extends Activity {
    private EditText etName;
    private EditText etPassword;
    private TextView tvLogin;
    private TextView tvRegister;

    private void assignViews() {
        etName = (EditText) findViewById(R.id.et_name);
        etPassword = (EditText) findViewById(R.id.et_pwd);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        assignViews();
        initEvent();
    }

    private void initEvent() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isHave=false;
                String name=etName.getText().toString().trim();
                String password=etPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)){
                    String username= SharedUtil.getString(LoginActivity.this,"username");
                    String pwd= SharedUtil.getString(LoginActivity.this,"password");

                    if (!TextUtils.isEmpty(username)){
                        String[] names=username.split(",");
                        String[] pwds=pwd.split(",");
                        for (int i=0;i<names.length;i++){
                            if (name.equals(names[i]) && password.equals(pwds[i])){
                                isHave=true;
                            }
                        }
                        if (isHave){
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this,"用户名和密码不对", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this,"用户名和密码不对", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,"请输入完整信息", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
