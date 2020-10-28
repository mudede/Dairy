package com.example.dairy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.dairy.R;
import com.example.dairy.util.SharedUtil;


public class RegisterActivity extends Activity {
    
    private EditText etName;
    private EditText etPassword;
    private EditText etRePassword;
    private TextView tvRegister;
    
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    
        etName = (EditText) findViewById(R.id.et_user_name);
        etPassword = (EditText) findViewById(R.id.et_pwd);
        etRePassword = (EditText) findViewById(R.id.et_repwd);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        
        
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isHave=false;
                String name=etName.getText().toString().trim();
                String password=etPassword.getText().toString().trim();
                String rePassword=etRePassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(rePassword)){
                    if (password.equals(rePassword)){
                        String username= SharedUtil.getString(RegisterActivity.this,"username");
                        if (username!=null && username.length()>0){
                            String[] names=username.split(",");
                            for (int i=0;i<names.length;i++){
                                if (name.equals(names[i])){
                                    isHave=true;
                                }
                            }

                            if (isHave){
                                Toast.makeText(RegisterActivity.this,"该用户名已被注册过，请更换用户名", Toast.LENGTH_SHORT).show();
                            }else {
                                String passwords= SharedUtil.getString(RegisterActivity.this,"password");
                                String newName=username+","+name;
                                String newPassword=passwords+","+password;
                                SharedUtil.putString(RegisterActivity.this,"username",newName);
                                SharedUtil.putString(RegisterActivity.this,"password",newPassword);
                                Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }else {
                            SharedUtil.putString(RegisterActivity.this,"username",name);
                            SharedUtil.putString(RegisterActivity.this,"password",password);
                            Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }else {
                        Toast.makeText(RegisterActivity.this,"两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"请输入完整信息", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
    }
}
