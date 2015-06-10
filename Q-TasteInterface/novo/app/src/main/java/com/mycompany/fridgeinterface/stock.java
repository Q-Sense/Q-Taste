package com.mycompany.fridgeinterface;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.fridgeinterface.databaseHandlesStock;

import java.util.ArrayList;
import java.util.List;


public class stock extends Activity {



    List<com.mycompany.fridgeinterface.Product> Productsstock = new ArrayList<Product>();
    ListView stockListView;


    ArrayAdapter<Product> adapterstock;


    final Context context=this;

    databaseHandlesStock dbHandlerStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock);


        /*        nameTxt = (EditText) findViewById(R.id.txtName);
        codeTxt = (EditText) findViewById(R.id.txtCode);
        quantityTxt = (EditText) findViewById(R.id.txtQuantity);
        durabilityTxt = (EditText) findViewById(R.id.txtDurability);*/


        stockListView = (ListView) findViewById(R.id.listView);

        dbHandlerStock = new databaseHandlesStock(getApplicationContext());



        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("My Products");
        tabHost.addTab(tabSpec);



        if (dbHandlerStock.getProductsCountStock() != 0)
            Productsstock.addAll(dbHandlerStock.getAllProductsStock());

        populateList();


    }

    /* private boolean productExists(Product product) {
         String name = product.getName();
         int productCount = Products.size();

         for (int i = 0; i < productCount; i++) {
             if (name.compareToIgnoreCase(Products.get(i).getName()) == 0)
                 return true;
         }
         return false;
     }
 */
    private void populateList() {
        adapterstock = new StockListAdapter();
        stockListView.setAdapter(adapterstock);
        //  ArrayAdapter<Product> adapterstock = new StockListAdapter();
        // stockListView.setAdapter(adapterstock);

    }
    //inserir na listview
    private class StockListAdapter extends ArrayAdapter<Product> {
        public StockListAdapter() {
            super(stock.this, R.layout.stock, Productsstock);
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {


            if (view == null)
                view = getLayoutInflater().inflate(R.layout.stock, parent, false);

            Product currentProduct = Productsstock.get(position);



            TextView name = (TextView) view.findViewById(R.id.productName);
            name.setText(currentProduct.getName());
            TextView code = (TextView) view.findViewById(R.id.code);
            code.setText(currentProduct.getCode());
            TextView quantity = (TextView) view.findViewById(R.id.quantity);
            quantity.setText(currentProduct.getQuantity());
            TextView durability = (TextView) view.findViewById(R.id.durability);
            durability.setText(currentProduct.getDurability());



            return view;
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maindb, menu);
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
