package com.example.wang.multithreadclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wang.multithreadclient.ClientThread;
import com.example.wang.multithreadclient.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity
{
    // 定义界面上的两个文本框
    EditText input;
    TextView show;
    String name;
    // 定义界面上的一个按钮
    Button send;
    Button clear;

    Handler handler;
    // 定义与服务器通信的子线程
    ClientThread clientThread;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("第二个程序真的打开了");

        Intent intent=getIntent();
        Person p=(Person) intent.getSerializableExtra("person");
        System.out.println("p获取完成");
        name=p.getName();
        System.out.println(p.getName());
        System.out.println("name获取完成");

        clear=(Button)findViewById(R.id.clear);
        input = (EditText) findViewById(R.id.input);
        send = (Button) findViewById(R.id.send);
        show = (TextView) findViewById(R.id.show);
        handler = new Handler() // ②
        {
            @Override
            public void handleMessage(Message msg)
            {
                // 如果消息来自于子线程
                if (msg.what == 0x123)
                {
                    // 将读取的内容追加显示在文本框中
                    show.append("\n" + msg.obj.toString());
                }
            }
        };
        clientThread = new ClientThread(handler);
        // 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据
        new Thread(clientThread).start(); // ①
        send.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    // 当用户按下发送按钮后，将用户输入的数据封装成Message
                    // 然后发送给子线程的Handler
                    System.out.println("马上开始获取日期了");
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
                    System.out.println(df.format(new Date()));
                    Message msg = new Message();
                    msg.what = 0x345;
                    msg.obj = name+df.format(new Date())+":"+input.getText().toString();
                    System.out.println( name+df.format(new Date())+":"+input.getText().toString());
                    clientThread.revHandler.sendMessage(msg);
                    // 清空input文本框
                    input.setText("");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                show.clearComposingText();
                show.setText("");
            }
        });
    }
}

