package com.mycompany.fridgeinterface;

/**
 * Created by Maria Brand�o on 07/05/2015.
 */

//import android.net.Uri;
public class Product {
    private String _name;
    private String _code;
    private String _quantity;
    private String _durability;
    private int _id;

    //base de dados
    public Product(int id, String name, String code, String quantity, String durability) {
        this._id = id;
        this._name = name;
        this._code = code;
        this._quantity = quantity;
        this._durability = durability;

    }


    //uma para cada variavel
    public int getId() {
        return this._id;
    }

    public String getName() {
        return this._name;
    }

    public String getCode() {
        return this._code;
    }

    public String getQuantity() {
        return this._quantity;
    }

    public String getDurability() {
        return this._durability;
    }


}
