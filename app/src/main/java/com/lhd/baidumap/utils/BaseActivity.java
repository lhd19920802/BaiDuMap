package com.lhd.baidumap.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lhd.baidumap.R;

public class BaseActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }
}
