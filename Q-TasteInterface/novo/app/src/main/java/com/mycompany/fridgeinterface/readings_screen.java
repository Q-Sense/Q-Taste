package com.mycompany.fridgeinterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class readings_screen extends ActionBarActivity {
    private Button mQrCodeButton;
    private Button mBarcode;
    private Button mNFC;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings_screen);
        final Context context=this;



        mQrCodeButton = (Button)findViewById(R.id.qrcode);
        mQrCodeButton.setOnClickListener(qrlacode);

        mBarcode = (Button)findViewById(R.id.barcode);
        mBarcode.setOnClickListener(barlacode);



       /* mNFC = (Button)findViewById(R.id.nfc);
        mNFC.setOnClickListener(new View.OnClickListener(){

    });*/
    }


    private View.OnClickListener barlacode = new View.OnClickListener(){
        public void onClick(View v){

            try {
                //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                Intent intent = new Intent(ACTION_SCAN);
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException anfe) {
                //on catch, show the download dialog
                showDialog(readings_screen.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
            }
        }};

    private View.OnClickListener qrlacode = new View.OnClickListener(){
    public void onClick(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(readings_screen.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }};


    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        final Context context=this;
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String cancer="10";
                String comp="0";
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();

                Intent intent2=new Intent(context, createdb.class);
                intent2.putExtra("contents", contents);
                intent2.putExtra("cancer", cancer);
                intent2.putExtra("comp", comp);
                startActivity(intent2);
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_readings_screen, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
