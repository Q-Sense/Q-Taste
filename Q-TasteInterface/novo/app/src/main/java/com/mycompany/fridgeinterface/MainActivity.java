package com.mycompany.fridgeinterface;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    private Button mReadingsButton;
    private Button mStockButton;
    private Button mSettingsButton;
    private Button mInfo;
    private Button mShowStock;

    //onCreate method is called when an instance of the activity subclass is created. When an activity is created, it needs a user interface to manage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context=this;
        mReadingsButton = (Button)findViewById(R.id.readings);
        mReadingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(context, readings_screen.class);
                startActivity(intent);
            }
        });
        mStockButton = (Button)findViewById(R.id.stock);
        mStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MainActivityDB.class);
                startActivity(intent);
            }
        });

        mSettingsButton = (Button)findViewById(R.id.settings);
        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(context, settings.class);
                startActivity(intent2);


            }
        });

        mShowStock = (Button)findViewById(R.id.btdStock);
        mShowStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2=new Intent(context, stock.class);
                startActivity(intent2);


            }
        });

        mInfo = (Button)findViewById(R.id.info);
        mInfo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(context, info.class);
                startActivity(intent2);

            }

            });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
