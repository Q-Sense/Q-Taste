package com.mycompany.fridgeinterface;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import java.util.GregorianCalendar;
import java.util.List;


public class createdb extends Activity {
    EditText nameTxt, codeTxt, quantityTxt, durabilityTxt;
    List<com.mycompany.fridgeinterface.Product> Products = new ArrayList<Product>();
    List<com.mycompany.fridgeinterface.Product> Productsstock = new ArrayList<Product>();
    ListView productListView;
    ListView stockListView;


    databaseHandles dbHandler;
    databaseHandlesStock dbHandlerStock;
    int j=-2;
    int k =-2;
    ArrayAdapter<Product> listadapter;
    ArrayAdapter<Product> adapterstock;
    String a="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindb);


        nameTxt = (EditText) findViewById(R.id.txtName);
        codeTxt = (EditText) findViewById(R.id.txtCode);
        quantityTxt = (EditText) findViewById(R.id.txtQuantity);
        durabilityTxt = (EditText) findViewById(R.id.txtDurability);

        productListView = (ListView) findViewById(R.id.listView);

        dbHandler = new databaseHandles(getApplicationContext());
        dbHandlerStock = new databaseHandlesStock(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        String yolo = extras.getString("comp");
        String aux = extras.getString("contents");
        if (yolo.equals(a))

        {
            codeTxt.setText(aux);
            final String aux2 = extras.getString("cancer");
            durabilityTxt.setText(aux2);
        }

        else

        {
            final String name_e = extras.getString("ename");
            nameTxt.setText(name_e);
            codeTxt.setText(aux);
            final String dura_e = extras.getString("edura");
            durabilityTxt.setText(dura_e);
            final String qnt_e = extras.getString("eqnt");
            quantityTxt.setText(qnt_e);

        }



        final Button addBtn = (Button) findViewById(R.id.btnAdd);
        registerForContextMenu(addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product(dbHandler.getProductsCount(), String.valueOf(nameTxt.getText()), String.valueOf(codeTxt.getText()), String.valueOf(quantityTxt.getText()), String.valueOf(durabilityTxt.getText()));
                Product productstock = new Product(dbHandlerStock.getProductsCountStock(), String.valueOf(nameTxt.getText()), String.valueOf(codeTxt.getText()), String.valueOf(quantityTxt.getText()), String.valueOf(durabilityTxt.getText()));

                //Botão de criar alarme que nos manda para o settings
                final Button criaA = (Button) findViewById(R.id.creAlarm);
                criaA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String durability, name, codigo;


                        Intent setIntent = new Intent(createdb.this,settings.class);


                        name = String.valueOf(nameTxt.getText());
                        setIntent.putExtra("nome", name);
                        startActivity(setIntent);


                        durability = String.valueOf(durabilityTxt.getText());
                        int durar = Integer.parseInt(durability);
                        setIntent.putExtra("Duracao", durar);
                        startActivity(setIntent);


                    }
                });


                if (productExists(product)==-1) {





                    dbHandler.createProduct(product);
                    Products.add(product);
                    dbHandlerStock.createProductStock(productstock);
                    Productsstock.add(productstock);
                    Toast.makeText(getApplicationContext(), String.valueOf(nameTxt.getText()) + " has been added to your Products!", Toast.LENGTH_SHORT).show();
                    return;


                }
                if (productExists(product) != -1) {
                    openContextMenu(view);
                    view.showContextMenu();
                    //nao funciona no primeiro produto de todos (so nos outros)


                }
            }
        });

        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addBtn.setEnabled(String.valueOf(nameTxt.getText()).trim().length() > 0);
             //   addBtn.setEnabled(String.valueOf(durabilityTxt.getText()).trim().length() > 0); nao funciona (se apagar o default de 10 deixa carregar no create product e nao devia )
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (dbHandler.getProductsCount() != 0)
            Products.addAll(dbHandler.getAllProducts());

        if (dbHandlerStock.getProductsCountStock() != 0)
            Productsstock.addAll(dbHandlerStock.getAllProductsStock());
       // populateList();
    }
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderTitle("Product already exists!");
        menu.add(Menu.NONE, '0', menu.NONE, "Edit Product");
        menu.add(Menu.NONE, '1', menu.NONE, "Delete Product");
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case '0':
                dbHandler.deleteProduct(Products.get(j + 1));
                Products.remove(j + 1);
                Product product = new Product(dbHandler.getProductsCount(), String.valueOf(nameTxt.getText()), String.valueOf(codeTxt.getText()), String.valueOf(quantityTxt.getText()), String.valueOf(durabilityTxt.getText()));
                dbHandler.createProduct(product);
                Products.add(product);
                Toast.makeText(getApplicationContext(), String.valueOf(nameTxt.getText()) + " was edited.", Toast.LENGTH_SHORT).show();

                break;
            case '1':
                dbHandler.deleteProduct(Products.get(j + 1));
                Products.remove(j + 1);
                //
                Toast.makeText(getApplicationContext(), String.valueOf(nameTxt.getText()) + " was removed from your stock list.", Toast.LENGTH_SHORT).show();


                break;
        }

        return super.onContextItemSelected(item);
    }


    private int productExists(Product product) {
        String name = product.getName();
        String code = product.getCode();
        int productCount = Products.size();

        for (int i = 0; i < productCount; i++) {
            if (name.compareToIgnoreCase(Products.get(i).getName()) == 0)
                return i;
            //nao deixa adicionar
            if (code.compareToIgnoreCase(Products.get(i).getCode()) == 0)
                return i;

            j=i;
        }

        return -1;
    }


    private String productstockExists(String codigo) {


        int productstockCount = Productsstock.size();


        for (int i = 0; i < productstockCount; i++) {
            if (codigo.equals(Productsstock.get(i).getCode()))

            {
                Toast.makeText(getApplicationContext(), String.valueOf(codigo) + " EXISTS", Toast.LENGTH_SHORT).show();
                k=i;
                String cproduct=codigo;
                return cproduct;
            }

        }


        return "123";
    }






    public void populateList() {
        listadapter = new ProductListAdapter();
        productListView.setAdapter(listadapter);
        adapterstock = new StockListAdapter();
        stockListView.setAdapter(adapterstock);


    }

    //inserir na listview
    private class ProductListAdapter extends ArrayAdapter<Product> {
        public ProductListAdapter() {
            super(createdb.this, R.layout.listview_item, Products);
        }

     @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Product currentProduct = Products.get(position);

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

    private class StockListAdapter extends ArrayAdapter<Product> {
        public StockListAdapter() {
            super (createdb.this, R.layout.stock, Productsstock);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.stock, parent, false);

            Product currentStockProduct = Productsstock.get(position);

            TextView name = (TextView) view.findViewById(R.id.productName);
            name.setText(currentStockProduct.getName());
            TextView phone = (TextView) view.findViewById(R.id.code);
            phone.setText(currentStockProduct.getCode());
            TextView email = (TextView) view.findViewById(R.id.quantity);
            email.setText(currentStockProduct.getQuantity());
            TextView address = (TextView) view.findViewById(R.id.durability);
            address.setText(currentStockProduct.getDurability());


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
