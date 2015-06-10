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

import com.mycompany.fridgeinterface.databaseHandles;

import java.util.ArrayList;
import java.util.List;


public class MainActivityDB extends Activity {



    List<com.mycompany.fridgeinterface.Product> Products = new ArrayList<Product>();
    ListView productListView;


    ArrayAdapter<Product> productadapter;


    final Context context=this;

    databaseHandles dbHandler;

    int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_item);

        productListView = (ListView) findViewById(R.id.listView);

        dbHandler = new databaseHandles(getApplicationContext());

        //ação do long click


        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("My Products");
        tabHost.addTab(tabSpec);



        if (dbHandler.getProductsCount() != 0)
            Products.addAll(dbHandler.getAllProducts());

        populateList();


    }


    private void populateList() {
        productadapter = new ProductListAdapter();
        productListView.setAdapter(productadapter);


    }
    //inserir na listview
    private class ProductListAdapter extends ArrayAdapter<Product> {
        public ProductListAdapter() {
            super(MainActivityDB.this, R.layout.listview_item, Products);
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {


            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            final Product currentProduct = Products.get(position);


            final Button mDelete =(Button)findViewById(R.id.delete);
            final Button edit =(Button)findViewById(R.id.edit);


            for ( p=0;p<=dbHandler.getProductsCount()-2;p++) {

            mDelete.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {


                 Product lala = Products.get((dbHandler.getProductsCount()-1)-p);
                 dbHandler.deleteProduct(lala);
                 Products.remove(lala);
                 productadapter.notifyDataSetChanged();
             }



            });

            }


            edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String e_name, e_code, e_dura, e_qnt;
                    String comp="1";
                    e_name=currentProduct.getName();
                    e_code=currentProduct.getCode();
                    e_dura=currentProduct.getDurability();
                    e_qnt=currentProduct.getQuantity();
                    Intent intent = new Intent(context, createdb.class);
                    intent.putExtra("comp", comp);
                    intent.putExtra("ename", e_name);
                    intent.putExtra("contents", e_code);
                    intent.putExtra("edura", e_dura);
                    intent.putExtra("eqnt", e_qnt);
                    startActivity(intent);
                    dbHandler.deleteProduct(currentProduct);
                    Products.remove(currentProduct);

                    productadapter.notifyDataSetChanged();
                }

            });




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
