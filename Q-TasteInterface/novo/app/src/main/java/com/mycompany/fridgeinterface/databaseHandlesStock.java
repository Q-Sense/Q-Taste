package com.mycompany.fridgeinterface;

/**
 * Created by Carolina on 19/05/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class databaseHandlesStock extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "productManagerStock",
            TABLE_PRODUCTS_S = "productsstock",
            KEY_ID_S = "idstock",
            KEY_NAME_S = "namestock",
            KEY_CODE_S = "codestock",
            KEY_QUANTITY_S = "quantitystock",
            KEY_DURABILITY_S = "durabilitystock";

    public databaseHandlesStock (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS_S + "(" + KEY_ID_S + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME_S + " TEXT," + KEY_CODE_S + " TEXT," + KEY_QUANTITY_S + " TEXT," + KEY_DURABILITY_S + " TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS_S);
        onCreate(db);
    }

    public void createProductStock(com.mycompany.fridgeinterface.Product product){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_CODE_S, product.getCode());
        values.put(KEY_NAME_S, product.getName());
        values.put(KEY_DURABILITY_S, product.getDurability());
        values.put(KEY_QUANTITY_S, product.getQuantity());


        db.insert(TABLE_PRODUCTS_S, null, values);
        db.close();

    }
    public com.mycompany.fridgeinterface.Product getProductStock(int id){
        SQLiteDatabase db= getReadableDatabase();

        Cursor cursor= db.query(TABLE_PRODUCTS_S, new String[]{KEY_ID_S, KEY_NAME_S, KEY_DURABILITY_S, KEY_QUANTITY_S, KEY_CODE_S }, KEY_ID_S + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if(cursor != null)
            cursor.moveToFirst();
        com.mycompany.fridgeinterface.Product product= new com.mycompany.fridgeinterface.Product(Integer.parseInt(cursor.getString(0)), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4) );
        db.close();
        cursor.close();
        return product;
    }

    public void deleteProductStock(com.mycompany.fridgeinterface.Product product) {
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_PRODUCTS_S, KEY_ID_S + "=?", new String[]{String.valueOf(product.getId())});
        db.close();
    }

    public int getProductsCountStock() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS_S, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateProductStock(com.mycompany.fridgeinterface.Product product) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_CODE_S, product.getCode());
        values.put(KEY_NAME_S, product.getName());
        values.put(KEY_DURABILITY_S, product.getDurability());
        values.put(KEY_QUANTITY_S, product.getQuantity());


        int rowsAffected = db.update(TABLE_PRODUCTS_S, values, KEY_ID_S + "=?", new String[] { String.valueOf(product.getId()) });
        db.close();

        return rowsAffected;
    }

    public List<com.mycompany.fridgeinterface.Product> getAllProductsStock() {
        List<com.mycompany.fridgeinterface.Product> products = new ArrayList<com.mycompany.fridgeinterface.Product>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS_S, null);

        if (cursor.moveToFirst()) {
            do {
                products.add(new com.mycompany.fridgeinterface.Product(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }

}
