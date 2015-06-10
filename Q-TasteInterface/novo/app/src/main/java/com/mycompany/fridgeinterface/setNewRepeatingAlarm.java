package com.mycompany.fridgeinterface;

/**
 * Created by Carolina on 31/05/2015.
 */
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.GregorianCalendar;

/**
 * Created by Carolina on 22/05/2015.
 */
public class setNewRepeatingAlarm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auxsettings);

        setNewrepeating();

    }

    public void setNewrepeating () {

        Intent newrepeating = new Intent(this, setAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, newrepeating, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Bundle extras = getIntent().getExtras();
        final int x = extras.getInt("Dura");
        Long alertTime = new GregorianCalendar().getTimeInMillis() + (x - 2) * 1000;

        if (manager != null) {
            manager.cancel(pendingIntent);
        }
// ele cancela o alarme mas nao faz o editText
        EditText editText = (EditText) findViewById(R.id.newtime);
        String aux = editText.getText().toString();
        int interval = (Integer.parseInt(aux)) * 1000;


        manager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, interval, pendingIntent);

        Toast.makeText(this, "New Alarm Set!", Toast.LENGTH_SHORT).show();

    }
}

