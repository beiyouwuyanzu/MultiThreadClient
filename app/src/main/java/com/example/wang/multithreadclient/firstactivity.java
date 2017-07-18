package com.example.wang.multithreadclient;

/**
 * Created by Wang on 2017/6/23.
 */

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class firstactivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buju);
        Button bn = (Button) findViewById(R.id.bn);
        System.out.println("第一个程序已启动");
        bn.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                EditText name = (EditText)findViewById(R.id.name);
                EditText passwd = (EditText)findViewById(R.id.passwd);
                RadioButton male = (RadioButton) findViewById(R.id.male);
                String gender = male.isChecked() ? "男 " : "女";
                Person p = new Person(name.getText().toString(), passwd
                        .getText().toString(), gender);
                System.out.println("person创建完成");
                // 创建一个Bundle对象
                Bundle data = new Bundle();
                data.putSerializable("person", p);
                System.out.println("data设置完成");
                // 创建一个Intent
                Intent intent = new Intent(firstactivity.this, MainActivity.class);
                intent.putExtras(data);
                System.out.println("data设置完成");
                // 启动intent对应的Activity
                startActivity(intent);
                System.out.println("第二个程序已经打开");
            }
        });
    }
}


