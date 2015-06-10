package com.mycompany.fridgeinterface;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;





/**
 * Created by Maria Brand�o on 07/05/2015.
 */
public class databaseHandles extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "productManager",
            TABLE_PRODUCTS = "products",
            KEY_ID = "id",
            KEY_NAME = "name",
            KEY_CODE = "code",
            KEY_QUANTITY = "quantity",
            KEY_DURABILITY = "durability";

    public databaseHandles(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_CODE + " TEXT," + KEY_QUANTITY + " TEXT," + KEY_DURABILITY + " TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void createProduct(com.mycompany.fridgeinterface.Product product){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_CODE, product.getCode());
        values.put(KEY_NAME, product.getName());
        values.put(KEY_DURABILITY, product.getDurability());
        values.put(KEY_QUANTITY, product.getQuantity());


        db.insert(TABLE_PRODUCTS, null, values);
        db.close();

    }
    public com.mycompany.fridgeinterface.Product getProduct(int id){
        SQLiteDatabase db= getReadableDatabase();

        Cursor cursor= db.query(TABLE_PRODUCTS, new String[]{KEY_ID, KEY_NAME, KEY_DURABILITY, KEY_QUANTITY, KEY_CODE }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if(cursor != null)
            cursor.moveToFirst();
        com.mycompany.fridgeinterface.Product product= new com.mycompany.fridgeinterface.Product(Integer.parseInt(cursor.getString(0)), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4) );
        db.close();
        cursor.close();
        return product;
    }

    public void deleteProduct(com.mycompany.fridgeinterface.Product product) {
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_ID + "=?", new String[]{String.valueOf(product.getId())});
        db.close();
    }

    public int getProductsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateProduct(com.mycompany.fridgeinterface.Product product) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_CODE, product.getCode());
        values.put(KEY_NAME, product.getName());
        values.put(KEY_DURABILITY, product.getDurability());
        values.put(KEY_QUANTITY, product.getQuantity());


        int rowsAffected = db.update(TABLE_PRODUCTS, values, KEY_ID + "=?", new String[] { String.valueOf(product.getId()) });
        db.close();

        return rowsAffected;
    }

    public List<com.mycompany.fridgeinterface.Product> getAllProducts() {
        List<com.mycompany.fridgeinterface.Product> products = new ArrayList<com.mycompany.fridgeinterface.Product>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);

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

