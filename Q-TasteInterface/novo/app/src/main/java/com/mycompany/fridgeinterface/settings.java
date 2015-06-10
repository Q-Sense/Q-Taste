package com.mycompany.fridgeinterface;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Carolina on 13/05/2015.
 */
public class settings extends Activity {


    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        final Intent alertIntent = new Intent(this,setAlarm.class);
        final Intent newrepeating = new Intent (this, setNewRepeatingAlarm.class);

        pendingIntent = PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Button criar = (Button) findViewById(R.id.criarnovo);
        criar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                OnonAlarm();

            }

        });


        final Button cancelar = (Button) findViewById(R.id.cancel);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        final Button setrepeat = (Button) findViewById(R.id.setrep);
        setrepeat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //setContentView(R.layout.auxsettings);
                startActivity(newrepeating);

            }
        });


        final Button sheshe = (Button) findViewById(R.id.schedule);
        sheshe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //espera por acção

            }
        });
    }

    public void OnonAlarm(){


        Bundle extras = getIntent().getExtras();
        final int x  = extras.getInt("Duracao");

        Bundle superextra = getIntent().getExtras();
        final String aux = superextra.getString("nome");

        Intent alertIntent = new Intent(this,setAlarm.class);
        alertIntent.putExtra("NOME",aux);

        Long alertTime = new GregorianCalendar().getTimeInMillis() + (x-2) * 1000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval = 8000;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,alertTime, interval, PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        Toast.makeText(this, "Alarm Set and Repeating", Toast.LENGTH_SHORT).show();
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    private void cancel(){

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);


        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();

    }

    //Esta função so irá alterar o tempo em que o alarme vai repetir - não está a ser usada. É usado o setNewRepeat
    public void setnewRepeat( ){
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Bundle extras = getIntent().getExtras();
        final int x  = extras.getInt("Duracao");
        Long alertTime = new GregorianCalendar().getTimeInMillis() + (x-2) * 1000;

        if (manager!= null) {
            manager.cancel(pendingIntent);
        }

        EditText editText = (EditText) findViewById(R.id.newtime);
        String aux = editText.getText().toString();
        int interval = (Integer.parseInt(aux))*1000;


        manager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, interval, pendingIntent);

        Toast.makeText(this, "New Alarm Set!", Toast.LENGTH_SHORT).show();

    }


    //schedule - ainda falta editar
    public void startAt10() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 30);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);
    }
}